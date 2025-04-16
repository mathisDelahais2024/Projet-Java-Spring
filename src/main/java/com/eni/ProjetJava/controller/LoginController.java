package com.eni.ProjetJava.controller;

import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private UtilisateurService utilisateurService;

    // Affichage de la page de connexion
    @GetMapping("/connexion")
    public String showLoginPage() {
        return "connexion";
    }

    @PostMapping("/connexion")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String motDePasse,
                        Model model,
                        HttpSession session) {

        Utilisateur utilisateur = utilisateurService.authentifier(email, motDePasse);

        if (utilisateur != null) {
            // ✅ Stocker l'utilisateur dans la session
            session.setAttribute("utilisateur", utilisateur);

            // Rediriger vers la page d'accueil
            return "redirect:/accueil";
        } else {
            // ❌ Afficher un message d'erreur
            model.addAttribute("erreur", "Email ou mot de passe incorrect");
            return "connexion";
        }
    }

    @GetMapping("/deconnexion")
    public String deconnexion(HttpSession session) {
        session.invalidate(); // ⛔ Supprime les données de session
        return "redirect:/accueil";  // 🔁 Redirection vers la page d'accueil
    }



    // Affichage de la page d'inscription
    @GetMapping("/inscription")
    public String showInscriptionPage() {
        return "inscription";
    }

    //Affiche le profil
    @GetMapping("/profil")
    public String showProfilPage() {
        return "profil";
    }
}
