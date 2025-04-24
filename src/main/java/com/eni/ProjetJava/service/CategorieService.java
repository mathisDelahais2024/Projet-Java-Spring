package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.Categorie;
import com.eni.ProjetJava.repo.CategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public List<Categorie> getAll() {
        return categorieRepository.findAll();
    }

    public Categorie getByLibelle(String libelle) {
        return categorieRepository.findByLibelle(libelle);
    }
}
