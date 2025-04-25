package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.repo.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eni.ProjetJava.service.ConstanteService.*;

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

    public String inscrireUtilisateur(String pseudo, String nom, String prenom, String email, Long telephone, String rue, String codePostal, String ville, String motDePasse, String confirmMotDePasse) {
        try {
            if (!motDePasse.equals(confirmMotDePasse)) {
                return "Mots de passe non identiques";
            }

            if (utilisateurRepository.findByEmail(email) != null) {
                return "Email déjà utilisé";
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
            utilisateur.setMotDePasse(passwordEncoder.encode(motDePasse)); // Hash du mot de passe
            utilisateur.setAdministrateur(false);

            utilisateurRepository.save(utilisateur);
            return null;
        } catch (Exception e) {
            return "Erreur lors de l'enregistrement de l'utilisateur : " + e.getMessage();
        }
    }

    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        Utilisateur utilisateurExist = utilisateurRepository.findByEmail(utilisateur.getEmail());
        if (utilisateurExist != null) {
            utilisateurExist.setPseudo(utilisateur.getPseudo());
            utilisateurExist.setNom(utilisateur.getNom());
            utilisateurExist.setPrenom(utilisateur.getPrenom());
            utilisateurExist.setTelephone(utilisateur.getTelephone());
            utilisateurExist.setRue(utilisateur.getRue());
            utilisateurExist.setCodePostal(utilisateur.getCodePostal());
            utilisateurExist.setVille(utilisateur.getVille());
            return utilisateurRepository.save(utilisateurExist);
        }
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
