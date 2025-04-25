package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.Enchere;
import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.repo.EnchereRepository;
import com.eni.ProjetJava.repo.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.eni.ProjetJava.service.ConstanteService.*;

@Service
public class EnchereService {

    @Autowired
    private EnchereRepository enchereRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public ReponseService<List<Enchere>> getAll() {
        List<Enchere> encheres = enchereRepository.findAll();
        return ReponseService.construireReponse(CD_SUCCESS, "Toutes les enchères récupérées", encheres);
    }

    public ReponseService<Enchere> getById(Long noEnchere) {
        Enchere enchere = enchereRepository.findById(noEnchere).orElse(null);
        if (enchere == null) {
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Enchère non trouvée", null);
        }
        return ReponseService.construireReponse(CD_SUCCESS, "Enchère trouvée", enchere);
    }

    public ReponseService<List<Enchere>> getEncheresPubliques(String libelle, String nomArticle) {
        List<Enchere> encheres = enchereRepository.findAll();

        System.out.println("Nombre d'enchères récupérées: " + encheres.size());

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


    public ReponseService<List<Enchere>> getEncheresParUtilisateur(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur == null) {
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Utilisateur non trouvé", null);
        }

        List<Enchere> encheres = enchereRepository.findAll().stream()
                .filter(e -> e.getEncherisseur() != null && e.getEncherisseur().getEmail().equals(email))
                .collect(Collectors.toList());

        return ReponseService.construireReponse(CD_SUCCESS, "Enchères de l'utilisateur récupérées", encheres);
    }

    public ReponseService<List<Enchere>> getEncheresGagnees(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur == null) {
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Utilisateur non trouvé", null);
        }

        List<Enchere> encheres = enchereRepository.findAll().stream()
                .filter(e -> e.getGagnant() != null && e.getGagnant().getEmail().equals(email))
                .collect(Collectors.toList());

        return ReponseService.construireReponse(CD_SUCCESS, "Enchères gagnées récupérées", encheres);
    }

    public ReponseService<List<Enchere>> getEncheresParEtat(String etat) {
        List<Enchere> encheres = enchereRepository.findAll().stream()
                .filter(e -> e.getEtat().equals(etat))
                .collect(Collectors.toList());

        return ReponseService.construireReponse(CD_SUCCESS, "Enchères filtrées par état", encheres);
    }

    public ReponseService<Enchere> updateEnchere(Long noEnchere, Enchere enchere) {
        if (!enchereRepository.existsById(noEnchere)) {
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Enchère non trouvée", null);
        }

        enchere.setNoEnchere(noEnchere);
        Enchere updatedEnchere = enchereRepository.save(enchere);

        return ReponseService.construireReponse(CD_SUCCESS, "Enchère mise à jour", updatedEnchere);
    }

    public ReponseService<Void> deleteEnchere(Long noEnchere) {
        if (!enchereRepository.existsById(noEnchere)) {
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Enchère non trouvée", null);
        }

        enchereRepository.deleteById(noEnchere);
        return ReponseService.construireReponse(CD_SUCCESS, "Enchère supprimée", null);
    }

    public ReponseService<Enchere> proposerEnchere(Long noArticle, String email, float montant) {
        Enchere enchereActuelle = enchereRepository.findById(noArticle).orElse(null);
        if (enchereActuelle == null) {
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Article non trouvé", null);
        }

        LocalDate aujourdHui = LocalDate.now();
        if (enchereActuelle.getArticle().getDateFinEncheres() != null &&
                !aujourdHui.isBefore(enchereActuelle.getArticle().getDateFinEncheres())) {
            return ReponseService.construireReponse(CD_ERR_BAD_REQUEST, "L'enchère est terminée", null);
        }

        Utilisateur encherisseur = utilisateurRepository.findByEmail(email);
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
            utilisateurRepository.save(ancien);
        }

        encherisseur.setCredit(encherisseur.getCredit() - montant);
        utilisateurRepository.save(encherisseur);

        enchereActuelle.setMontantEnchere(montant);
        enchereActuelle.setDateEnchere(LocalDate.now());
        enchereActuelle.setEncherisseur(encherisseur);
        enchereActuelle.setGagnant(encherisseur);

        enchereRepository.save(enchereActuelle);

        return ReponseService.construireReponse(CD_SUCCESS, "Enchère proposée avec succès", enchereActuelle);
    }

    public List<Enchere> rechercherEncheres(String libelle, String nomArticle) {
        return getEncheresPubliques(libelle, nomArticle).getData();
    }
}
