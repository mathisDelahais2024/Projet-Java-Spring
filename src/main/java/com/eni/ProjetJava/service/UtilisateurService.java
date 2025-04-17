package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.dao.IDAOUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    @Autowired
    private IDAOUtilisateur utilisateurDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
}