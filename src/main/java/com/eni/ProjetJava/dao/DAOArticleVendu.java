package com.eni.ProjetJava.dao;

import com.eni.ProjetJava.bo.ArticleVendu;
import com.eni.ProjetJava.bo.Categorie;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DAOArticleVendu implements IDAOArticleVendu {

    public List<ArticleVendu> articleVendus = new ArrayList<ArticleVendu>();

    Categorie categorie1 = new Categorie("1", "test");
    Categorie categorie2 = new Categorie("2", "test");
    Categorie categorie3 = new Categorie("3", "test");

    public DAOArticleVendu(){
        articleVendus.add(new ArticleVendu("1", "Toto", "Desription Toto", LocalDate.of(2025, 4, 15), LocalDate.of(2025, 4, 30), 20, 30, "bon", categorie1));
        articleVendus.add(new ArticleVendu("2", "Chocolatine", "Desription Chocolatine", LocalDate.of(2025, 5, 7), LocalDate.of(2025, 5, 15), 10, 40, "mauvais", categorie2));
        articleVendus.add(new ArticleVendu("3", "Beurre Doux", "Desription Beurre Doux", LocalDate.of(2025, 4, 30), LocalDate.of(2025, 5, 6), 7, 12, "bon", categorie3));
    }

    @Override
    public List<ArticleVendu> selectAll() {
        return articleVendus;
    }

    @Override
    public ArticleVendu selectById(String noArticle) {
        return articleVendus.stream()
                .filter(article -> article.getNoArticle().equals(noArticle))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void insert(ArticleVendu articleVendu) {
        articleVendus.add(articleVendu);
    }

    @Override
    public void update(ArticleVendu articleVendu) {
        delete(articleVendu.getNoArticle());
        insert(articleVendu);
    }

    @Override
    public void delete(String noArticle) {
        articleVendus.removeIf(article -> article.getNoArticle().equals(noArticle));
    }

    @Override
    public List<ArticleVendu> selectByNomAndCategorie(String nom, String noCategorie) {
        return articleVendus.stream()
                .filter(article ->
                        (nom == null || article.getNomArticle().toLowerCase().contains(nom.toLowerCase())) &&
                                (noCategorie == null || article.getCategorie().getNoCategorie().equals(noCategorie))
                )
                .toList();
    }
}
