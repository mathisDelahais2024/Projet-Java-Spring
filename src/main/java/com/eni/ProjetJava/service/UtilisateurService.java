package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.Utilisateur;

public interface UtilisateurService {
    Utilisateur authentifier(String email, String motDePasse);
}
