package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.Utilisateur;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Override
    public Utilisateur authentifier(String email, String motDePasse) {
        // 🧪 Ici tu peux tester avec une valeur en dur
        if ("test@mail.com".equals(email) && "1234".equals(motDePasse)) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setEmail(email);
            utilisateur.setPseudo("TestUser");
            return utilisateur;
        }

        return null;
    }
}
