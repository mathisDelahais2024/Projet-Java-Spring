package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.dao.IDAOUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    @Autowired
    private IDAOUtilisateur utilisateurDao;

    public Utilisateur findByEmail(String email) {
        return utilisateurDao.findByEmail(email);
    }
}