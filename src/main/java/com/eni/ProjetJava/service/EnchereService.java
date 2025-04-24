package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.Enchere;
import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.dao.IDAOEnchere;
import com.eni.ProjetJava.dao.IDAOUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

import static com.eni.ProjetJava.service.ConstanteService.*;

@Service
public class EnchereService {

    @Autowired
    private IDAOEnchere daoEnchere;

    @Autowired
    private IDAOUtilisateur daoUtilisateur;

    public ReponseService<List<Enchere>> getAll() {
        List<Enchere> encheres = daoEnchere.selectAll();
        return ReponseService.construireReponse(CD_SUCCESS, "Toutes les enchères récupérées", encheres);
    }

    public ReponseService<Enchere> getById(String id) {
        Enchere enchere = daoEnchere.selectById(id);
        if (enchere == null) {
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Enchère non trouvée", null);
        }
        return ReponseService.construireReponse(CD_SUCCESS, "Enchère trouvée", enchere);
    }

    public ReponseService<List<Enchere>> getEncheresPubliques(String libelle, String nomArticle) {
        List<Enchere> encheres = daoEnchere.selectAll();

        List<Enchere> encheresFiltrees = encheres.stream().filter(e -> {
            boolean filtreNom = (nomArticle == null || nomArticle.isEmpty())
                    || e.getArticle().getNomArticle().toLowerCase().contains(nomArticle.toLowerCase());

            boolean filtreCategorie = (libelle == null || libelle.isEmpty())
                    || (e.getArticle().getCategorie() != null
                    && e.getArticle().getCategorie().getLibelle().equals(libelle));

            return filtreNom && filtreCategorie;
        }).collect(Collectors.toList());

        return ReponseService.construireReponse(CD_SUCCESS, "Enchères filtrées récupérées", encheresFiltrees);
    }

    public ReponseService<List<Enchere>> getEncheresParUtilisateur(String utilisateurEmail) {
        Utilisateur utilisateur = daoUtilisateur.findByEmail(utilisateurEmail);

        if (utilisateur == null) {
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Utilisateur non trouvé", null);
        }

        List<Enchere> encheres = daoEnchere.selectAll();
        List<Enchere> encheresUtilisateur = encheres.stream()
                .filter(enchere -> enchere.getEncherisseur() != null &&
                        enchere.getEncherisseur().getNoUtilisateur().equals(utilisateur.getNoUtilisateur()))
                .collect(Collectors.toList());

        return ReponseService.construireReponse(CD_SUCCESS, "Enchères de l'utilisateur récupérées", encheresUtilisateur);
    }

    public ReponseService<List<Enchere>> getEncheresGagnees(String utilisateurEmail) {
        Utilisateur utilisateur = daoUtilisateur.findByEmail(utilisateurEmail);

        if (utilisateur == null) {
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Utilisateur non trouvé", null);
        }

        List<Enchere> encheres = daoEnchere.selectAll();
        List<Enchere> encheresGagnees = encheres.stream()
                .filter(enchere -> enchere.getGagnant() != null && enchere.getGagnant().getEmail().equals(utilisateurEmail))
                .collect(Collectors.toList());

        return ReponseService.construireReponse(CD_SUCCESS, "Enchères gagnées récupérées", encheresGagnees);
    }


    public ReponseService<List<Enchere>> getEncheresParEtat(String etat) {
        List<Enchere> encheres = daoEnchere.selectAll();
        List<Enchere> encheresFiltrees = encheres.stream()
                .filter(enchere -> enchere.getEtat().equals(etat))
                .collect(Collectors.toList());

        return ReponseService.construireReponse(CD_SUCCESS, "Enchères filtrées par état récupérées", encheresFiltrees);
    }

    public ReponseService<Enchere> proposerEnchere(String noArticle, String email, float montant) {
        Enchere enchereActuelle = daoEnchere.selectById(noArticle);
        if (enchereActuelle == null) {
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Article non trouvé", null);
        }

        LocalDate aujourdHui = LocalDate.now();
        if (enchereActuelle.getArticle().getDateFinEncheres() != null &&
                !aujourdHui.isBefore(enchereActuelle.getArticle().getDateFinEncheres())) {
            return ReponseService.construireReponse(CD_ERR_BAD_REQUEST, "L'enchère est terminée", null);
        }

        Utilisateur encherisseur = daoUtilisateur.findByEmail(email);
        if (encherisseur == null) {
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Utilisateur non trouvé", null);
        }

        if (montant <= enchereActuelle.getMontantEnchere()) {
            return ReponseService.construireReponse(CD_ERR_BAD_REQUEST, "Le montant doit être supérieur à l'enchère actuelle", null);
        }

        if (encherisseur.getCredit() < montant) {
            return ReponseService.construireReponse(CD_ERR_BAD_REQUEST, "Crédit insuffisant", null);
        }

        Utilisateur ancien = enchereActuelle.getGagnant();
        if (ancien != null && !ancien.getEmail().equals(encherisseur.getEmail())) {
            ancien.setCredit(ancien.getCredit() + enchereActuelle.getMontantEnchere());
            daoUtilisateur.update(ancien);
        }

        encherisseur.setCredit(encherisseur.getCredit() - montant);
        daoUtilisateur.update(encherisseur);

        enchereActuelle.setMontantEnchere(montant);
        enchereActuelle.setDateEnchere(LocalDate.now());
        enchereActuelle.setEncherisseur(encherisseur);
        enchereActuelle.setGagnant(encherisseur);

        daoEnchere.update(enchereActuelle);

        return ReponseService.construireReponse(CD_SUCCESS, "Enchère proposée avec succès", enchereActuelle);
    }

    public List<Enchere> rechercherEncheres(String libelle, String nomArticle) {
        return getEncheresPubliques(libelle, nomArticle).getData();
    }

    public ReponseService<Enchere> confirmerRetrait(String idArticle) {
        Enchere enchere = daoEnchere.selectById(idArticle);
        if (enchere == null) {
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Enchère non trouvée", null);
        }

        enchere.setRetraitEffectue(true);
        daoEnchere.update(enchere);

        return ReponseService.construireReponse(CD_SUCCESS, "Retrait confirmé", enchere);
    }

}
