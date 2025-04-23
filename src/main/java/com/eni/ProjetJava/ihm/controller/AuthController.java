package com.eni.ProjetJava.ihm.controller;

import com.eni.ProjetJava.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;


@Controller
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/")
    public String home() {
        return "accueil";
    }

    @GetMapping("/connexion")
    public String showLoginPage() {
        return "auth/connexion";
    }

    @GetMapping("/deconnexion")
    public String deconnexion(HttpSession session) {
        session.invalidate();
        return "redirect:/accueil";
    }

    @GetMapping("/inscription")
    public String showInscriptionPage() {
        return "auth/inscription";
    }

    @GetMapping("/profil")
    public String showProfilPage() {
        return "profil/profil";
    }
}
