package com.eni.ProjetJava.controlleur;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/connexion")
    public String showLoginPage() {
        // Retourne le nom du fichier HTML, sans l'extension .html
        return "connexion";
    }
}
