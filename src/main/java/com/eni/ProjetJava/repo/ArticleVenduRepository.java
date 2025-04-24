package com.eni.ProjetJava.repo;

import com.eni.ProjetJava.bo.ArticleVendu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleVenduRepository extends JpaRepository<ArticleVendu, Long> {
    List<ArticleVendu> findByNomArticleContainingAndCategorieNoCategorie(String nom, Long noArticle);
}
