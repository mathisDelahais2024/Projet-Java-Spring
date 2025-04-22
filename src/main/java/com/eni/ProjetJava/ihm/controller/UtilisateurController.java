package com.eni.ProjetJava.ihm.controller;

import com.eni.ProjetJava.service.ConstanteService;
import com.eni.ProjetJava.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/supprimer-utilisateur")
    public String supprimerUtilisateur(@RequestParam("email") String email) {
        String result = utilisateurService.supprimerUtilisateurParEmail(email);
        if (ConstanteService.CD_SUCCESS.equals(result)) {
            return "redirect:/home";
        }
        return "profil";
    }
}
