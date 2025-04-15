package com.eni.ProjetJava.dao;

import com.eni.ProjetJava.bo.ArticleVendu;

import java.util.List;

public interface IDAOArticleVendu {
    List<ArticleVendu> selectAll();
    ArticleVendu selectById(String noArticle);
    void insert(ArticleVendu articleVendu);
    void update(ArticleVendu articleVendu);
    void delete(String noArticle);
}
