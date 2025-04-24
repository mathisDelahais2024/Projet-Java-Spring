package com.eni.ProjetJava.dao;

import com.eni.ProjetJava.bo.Enchere;
import com.eni.ProjetJava.bo.ArticleVendu;
import com.eni.ProjetJava.bo.Categorie;
import com.eni.ProjetJava.bo.Retrait;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DAOEnchere implements IDAOEnchere {

    private List<Enchere> encheres = new ArrayList<>();

    public DAOEnchere() {

        Retrait retrait = new Retrait("10 rue des Pinson", "44000", "Nantes");

        Categorie categorie1 = new Categorie("1", "Électronique");
        Categorie categorie2 = new Categorie("2", "Meubles");

        ArticleVendu article1 = new ArticleVendu("1", "Smartphone", "Un smartphone haut de gamme", null, null, 100, 150, "bon", categorie1, retrait);
        ArticleVendu article2 = new ArticleVendu("2", "Canapé", "Canapé en cuir confortable", null, null, 50, 70, "bon", categorie2, retrait);

        Enchere enchere1 = new Enchere();
        enchere1.setArticle(article1);
        enchere1.setMontantEnchere(120);

        Enchere enchere2 = new Enchere();
        enchere2.setArticle(article2);
        enchere2.setMontantEnchere(60);

        encheres.add(enchere1);
        encheres.add(enchere2);
    }

    @Override
    public List<Enchere> selectAll() {
        return encheres;
    }

    @Override
    public Enchere selectById(String id) {
        return encheres.stream()
                .filter(enchere -> enchere.getArticle().getNoArticle().equals(String.valueOf(id)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Enchere enchere) {
        for (int i = 0; i < encheres.size(); i++) {
            if (encheres.get(i).getArticle().getNoArticle().equals(enchere.getArticle().getNoArticle())) {
                encheres.set(i, enchere);
                break;
            }
        }
    }
}
