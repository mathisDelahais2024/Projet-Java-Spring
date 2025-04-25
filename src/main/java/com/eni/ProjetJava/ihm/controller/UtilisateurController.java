package com.eni.ProjetJava.ihm.controller;

import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.service.ConstanteService;
import com.eni.ProjetJava.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    // Modifier l'utilisateur
    @PostMapping("/modifier-utilisateur")
    public String modifierUtilisateur(@ModelAttribute("utilisateur") Utilisateur utilisateur, HttpSession session) {
        utilisateurService.updateUtilisateur(utilisateur);
        session.setAttribute("utilisateur", utilisateur); // Mettre à jour l'utilisateur dans la session
        return "redirect:/profil"; // Rediriger vers la page de profil après modification
    }
}
