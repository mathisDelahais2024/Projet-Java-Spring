package com.eni.ProjetJava.controller;

import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UtilisateurService utilisateurService;

    // Affichage de la page de connexion
    @GetMapping("/connexion")
    public String showLoginPage() {
        return "connexion";
    }

    // Traitement de la connexion
    @PostMapping("/connexion")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String motDePasse,
                        Model model) {

        Utilisateur utilisateur = utilisateurService.authentifier(email, motDePasse);

        if (utilisateur != null) {
            // Connexion réussie, redirection vers l'accueil
            return "redirect:/accueil";
        } else {
            // Connexion échouée, on renvoie un message d'erreur
            model.addAttribute("erreur", "Email ou mot de passe incorrect");
            return "connexion";
        }
    }

    // Affichage de la page d'inscription
    @GetMapping("/inscription")
    public String showInscriptionPage() {
        return "inscription";
    }
}
