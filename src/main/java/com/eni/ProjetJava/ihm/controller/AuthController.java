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
        return "redirect:/";
    }

    @GetMapping("/inscription-form")
    public String afficherInscriptionForm() {
        return "auth/inscription";
    }

    @PostMapping("/inscription")
    public String inscrireUtilisateur(
            @RequestParam String pseudo,
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String email,
            @RequestParam(required = false) long telephone,
            @RequestParam(required = false) String rue,
            @RequestParam(required = false) String codePostal,
            @RequestParam(required = false) String ville,
            @RequestParam String motDePasse,
            @RequestParam String confirmation,
            Model model
    ) {
        // Appel du service d'inscription
        String erreur = utilisateurService.inscrireUtilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, confirmation);

        // Vérifier si une erreur a eu lieu pendant l'inscription
        if (erreur != null) {
            model.addAttribute("erreur", erreur);
            return "auth/inscription"; // Rediriger vers la page d'inscription en cas d'erreur
        }

        return "redirect:/connexion"; // Rediriger vers la page de connexion après l'inscription
    }

    @GetMapping("/profil")
    public String showProfilPage() {
        return "profil/profil";
    }

    @GetMapping("/vendre")
    public String showVendrePage() {
        return "enchere/vendre-article";
    }
}
