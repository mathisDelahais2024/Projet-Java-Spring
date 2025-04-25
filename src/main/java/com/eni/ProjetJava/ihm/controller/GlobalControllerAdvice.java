package com.eni.ProjetJava.ihm.controller;

import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.security.EniUserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addUserToModel(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof EniUserDetails eniUserDetails) {
            Utilisateur utilisateur = eniUserDetails.getUtilisateur();
            model.addAttribute("utilisateur", utilisateur);
            session.setAttribute("utilisateur", utilisateur);
        }
    }
}
