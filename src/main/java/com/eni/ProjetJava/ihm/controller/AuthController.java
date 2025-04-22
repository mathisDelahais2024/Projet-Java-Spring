package com.eni.ProjetJava.ihm.controller;

import com.eni.ProjetJava.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("pseudo") String pseudo,
                               @RequestParam("nom") String nom,
                               @RequestParam("prenom") String prenom,
                               @RequestParam("email") String email,
                               @RequestParam("telephone") long telephone,
                               @RequestParam("rue") String rue,
                               @RequestParam("codePostal") String codePostal,
                               @RequestParam("ville") String ville,
                               @RequestParam("motDePasse") String motDePasse,
                               @RequestParam("confirmMotDePasse") String confirmMotDePasse,
                               Model model) {

        String errorMessage = utilisateurService.inscrireUtilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, confirmMotDePasse);

        if (errorMessage != null) {
            model.addAttribute("error", errorMessage);
            return "register";
        }

        return "redirect:/login";
    }
}
