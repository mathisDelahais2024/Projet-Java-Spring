package com.eni.ProjetJava.ihm.controller;

import org.springframework.ui.Model;
import com.eni.ProjetJava.bo.Enchere;
import com.eni.ProjetJava.service.EnchereService;
import com.eni.ProjetJava.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;


@Controller
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private EnchereService enchereService;

    @GetMapping("/")
    public String afficherAccueil(
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) String search,
            Model model
    ) {
        List<Enchere> encheres;

        if ((categorie != null && !categorie.isEmpty()) || (search != null && !search.isEmpty())) {
            encheres = enchereService.rechercherEncheres(categorie, search);
        } else {
            encheres = enchereService.getEncheresPubliques(null, null).getData();
        }

        model.addAttribute("encheresPubliques", encheres);
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
