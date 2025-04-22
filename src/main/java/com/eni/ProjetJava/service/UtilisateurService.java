package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.dao.IDAOUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eni.ProjetJava.service.ConstanteService.CD_ERR_NOT_FOUND;
import static com.eni.ProjetJava.service.ConstanteService.CD_SUCCESS;

@Service
public class UtilisateurService {

    @Autowired
    private IDAOUtilisateur utilisateurDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ReponseService<List<Utilisateur>> getAll() {
        List<Utilisateur> utilisateur = utilisateurDao.selectAll();

        if (utilisateur.isEmpty()){
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Liste d'utilisateurs vide", utilisateur);
        }

        return ReponseService.construireReponse(CD_SUCCESS, "Liste des utilisateurs récupérée", utilisateur);
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurDao.findByEmail(email);
    }

    public void save(Utilisateur utilisateur) {
        utilisateurDao.save(utilisateur);
    }
    public String inscrireUtilisateur(String pseudo, String nom, String prenom, String email, long telephone, String rue, String codePostal, String ville, String motDePasse, String confirmMotDePasse) {
        if (!motDePasse.equals(confirmMotDePasse)) {
            return ConstanteService.CD_ERR_BAD_REQUEST;
        }

        if (utilisateurDao.findByEmail(email) != null) {
            return ConstanteService.CD_ERR_CONFLICT;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo(pseudo);
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);
        utilisateur.setTelephone(telephone);
        utilisateur.setRue(rue);
        utilisateur.setCodePostal(codePostal);
        utilisateur.setVille(ville);
        utilisateur.setMotDePasse(passwordEncoder.encode(motDePasse));
        utilisateur.setAdministrateur(false);

        utilisateurDao.save(utilisateur);

        return null;
    }

    public String supprimerUtilisateurParEmail(String email) {
        Utilisateur utilisateur = utilisateurDao.findByEmail(email);
        if (utilisateur == null) {
            return ConstanteService.CD_ERR_NOT_FOUND;
        }
        utilisateurDao.deleteByEmail(email);
        return ConstanteService.CD_SUCCESS;
    }
}
