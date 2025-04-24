package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.repo.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eni.ProjetJava.service.ConstanteService.CD_ERR_NOT_FOUND;
import static com.eni.ProjetJava.service.ConstanteService.CD_ERR_BAD_REQUEST;
import static com.eni.ProjetJava.service.ConstanteService.CD_ERR_CONFLICT;
import static com.eni.ProjetJava.service.ConstanteService.CD_SUCCESS;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ReponseService<List<Utilisateur>> getAll() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();

        if (utilisateurs.isEmpty()) {
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Liste d'utilisateurs vide", utilisateurs);
        }

        return ReponseService.construireReponse(CD_SUCCESS, "Liste des utilisateurs récupérée", utilisateurs);
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public void save(Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
    }

    public String inscrireUtilisateur(String pseudo, String nom, String prenom, String email, long telephone, String rue, String codePostal, String ville, String motDePasse, String confirmMotDePasse) {
        // Vérification des mots de passe
        if (!motDePasse.equals(confirmMotDePasse)) {
            return CD_ERR_BAD_REQUEST;
        }

        // Vérification si l'email existe déjà
        if (utilisateurRepository.findByEmail(email) != null) {
            return CD_ERR_CONFLICT;
        }

        // Création de l'utilisateur
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

        // Sauvegarde dans la base de données
        utilisateurRepository.save(utilisateur);

        return null;
    }

    public String supprimerUtilisateurParEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur == null) {
            return CD_ERR_NOT_FOUND;
        }

        utilisateurRepository.delete(utilisateur);
        return CD_SUCCESS;
    }
}
