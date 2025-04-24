package com.eni.ProjetJava.dao;

import com.eni.ProjetJava.bo.Categorie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DAOCategorie implements IDAOCategorie {

    private List<Categorie> categories = new ArrayList<>();

    public DAOCategorie() {
        categories.add(new Categorie("1", "Électronique"));
        categories.add(new Categorie("2", "Meubles"));
        categories.add(new Categorie("3", "Livres"));
        categories.add(new Categorie("4", "Vêtements"));
    }

    @Override
    public List<Categorie> selectAll() {
        return categories;
    }
}
