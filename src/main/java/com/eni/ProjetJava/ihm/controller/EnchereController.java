package com.eni.ProjetJava.ihm.controller;

import com.eni.ProjetJava.bo.Enchere;
import com.eni.ProjetJava.service.EnchereService;
import com.eni.ProjetJava.service.ReponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eni.ProjetJava.service.ConstanteService.*;

@Controller
@RequestMapping("/enchere")
public class EnchereController {

    @Autowired
    private EnchereService enchereService;

    @GetMapping("/getAll")
    public String getAllEncheres(Model model) {
        ReponseService<List<Enchere>> reponse = enchereService.getAll();
        model.addAttribute("encheres", reponse.getData());
        return "liste-encheres.html";
    }

    @GetMapping("/getById")
    public String getEnchereById(@RequestParam String id, Model model) {
        ReponseService<Enchere> reponse = enchereService.getById(id);
        if (reponse.getCode().equals(CD_ERR_NOT_FOUND)) {
            return "error-page.html";
        }
        model.addAttribute("enchere", reponse.getData());
        return "enchere/details-enchere.html";
    }

    @GetMapping("/public")
    public String afficherEncheresPubliques(@RequestParam(required = false) String categorie, @RequestParam(required = false) String nom, Model model
    ) {
        ReponseService<List<Enchere>> reponse = enchereService.getEncheresPubliques(categorie, nom);
        model.addAttribute("encheres", reponse.getData());
        return "encheres-publiques.html";
    }

    @GetMapping("/encheres/participations")
    public String getEncheresParUtilisateur(@RequestParam("utilisateurId") String utilisateurId, Model model) {
        ReponseService<List<Enchere>> response = enchereService.getEncheresParUtilisateur(utilisateurId);
        model.addAttribute("encheres", response.getData());
        return "encheres-participations";
    }

    @GetMapping("/encheres/gagnees")
    public String getEncheresGagnees(@RequestParam("utilisateurId") String utilisateurId, Model model) {
        ReponseService<List<Enchere>> response = enchereService.getEncheresGagnees(utilisateurId);
        model.addAttribute("encheres", response.getData());
        return "encheres-gagnees";
    }

    @GetMapping("/encheres/etat")
    public String getEncheresParEtat(@RequestParam("etat") String etat, Model model) {
        ReponseService<List<Enchere>> response = enchereService.getEncheresParEtat(etat);
        model.addAttribute("encheres", response.getData());
        return "encheres-filtrees";
    }

    @PostMapping("/proposer")
    public String proposerEnchere(
            @RequestParam String idArticle,
            @RequestParam String email,
            @RequestParam float montant,
            Model model) {

        ReponseService<Enchere> reponse = enchereService.proposerEnchere(idArticle, email, montant);

        if (!CD_SUCCESS.equals(reponse.getCode())) {
            model.addAttribute("error", reponse.getMessage());
            return "error-page";
        }

        model.addAttribute("enchere", reponse.getData());
        model.addAttribute("message", "Le nouveau prix est " + montant + " points.");
        return "details-enchere"; // <-- important si tu veux rester sur la même page
    }

    @PostMapping("/retrait")
    public String confirmerRetrait(@RequestParam String id, Model model) {
        ReponseService<Enchere> reponse = enchereService.confirmerRetrait(id);

        if (!CD_SUCCESS.equals(reponse.getCode())) {
            model.addAttribute("error", reponse.getMessage());
            return "error-page";
        }

        model.addAttribute("enchere", reponse.getData());
        model.addAttribute("message", "Retrait confirmé avec succès !");
        return "details-enchere";
    }


}
