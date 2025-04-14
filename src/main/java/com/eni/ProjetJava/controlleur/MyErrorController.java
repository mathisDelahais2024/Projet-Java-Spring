package com.eni.ProjetJava.controlleur;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Logique pour traiter l'erreur
        return "error";  // Retourne la page error.html dans les templates
    }
}


