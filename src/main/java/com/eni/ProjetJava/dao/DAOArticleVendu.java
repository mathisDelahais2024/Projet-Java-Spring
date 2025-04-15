package com.eni.ProjetJava.dao;

import com.eni.ProjetJava.bo.ArticleVendu;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DAOArticleVendu implements IDAOArticleVendu {

    public List<ArticleVendu> articleVendus = new ArrayList<ArticleVendu>();

    public DAOArticleVendu(){
        articleVendus.add(new ArticleVendu("1", "Toto", "Desription Toto", LocalDate.of(2025, 4, 15), LocalDate.of(2025, 4, 30), 20, 30, "bon"));
        articleVendus.add(new ArticleVendu("2", "Chocolatine", "Desription Chocolatine", LocalDate.of(2025, 5, 7), LocalDate.of(2025, 5, 15), 10, 40, "mauvais"));
        articleVendus.add(new ArticleVendu("3", "Beurre Doux", "Desription Beurre Doux", LocalDate.of(2025, 4, 30), LocalDate.of(2025, 5, 6), 7, 12, "bon"));
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
}
