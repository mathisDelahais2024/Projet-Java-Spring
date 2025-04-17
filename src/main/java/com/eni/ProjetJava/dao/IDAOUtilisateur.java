package com.eni.ProjetJava.dao;

import com.eni.ProjetJava.bo.Utilisateur;

import java.util.List;

public interface IDAOUtilisateur {
    List<Utilisateur> selectAll();
    Utilisateur findByEmail(String email);
    void save(Utilisateur utilisateur);
}
