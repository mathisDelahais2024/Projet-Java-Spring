package com.eni.ProjetJava.ihm.controller;

import com.eni.ProjetJava.bo.Categorie;
import com.eni.ProjetJava.bo.Enchere;
import com.eni.ProjetJava.service.CategorieService;
import com.eni.ProjetJava.service.EnchereService;
import com.eni.ProjetJava.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private EnchereService enchereService;

    @Autowired
    private CategorieService categorieService;

    @GetMapping("/")
    public String afficherAccueil(
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) String search,
            Model model
    ) {
        List<Enchere> encheres;
        List<Categorie> categories = categorieService.getAll();  // <- utilise le service

        if ((categorie != null && !categorie.isEmpty()) || (search != null && !search.isEmpty())) {
            encheres = enchereService.rechercherEncheres(categorie, search);
        } else {
            encheres = enchereService.getEncheresPubliques(null, null).getData();
        }

        model.addAttribute("categories", categories);
        model.addAttribute("encheresPubliques", encheres);
        model.addAttribute("categorie", categorie);
        model.addAttribute("search", search);

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
