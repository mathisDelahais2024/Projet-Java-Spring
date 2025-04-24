package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.ArticleVendu;
import com.eni.ProjetJava.bo.Retrait;
import com.eni.ProjetJava.repo.ArticleVenduRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eni.ProjetJava.service.ConstanteService.*;

@Service
public class ArticleVenduService {

    @Autowired
    private ArticleVenduRepository articleVenduRepository;

    public ReponseService<List<ArticleVendu>> getAll() {
        List<ArticleVendu> articles = articleVenduRepository.findAll();

        if (articles.isEmpty()){
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Liste d'articles vide", articles);
        }

        return ReponseService.construireReponse(CD_SUCCESS, "Liste des articles récupérée", articles);
    }

    public ReponseService<ArticleVendu> getByNoArticle(Long noArticle) {
        ArticleVendu article = articleVenduRepository.findById(Long.valueOf(noArticle)).orElse(null);

        if (article == null){
            return ReponseService.construireReponse(CD_ERR_NOT_FOUND, "Article non existent", article);
        }

        return ReponseService.construireReponse(CD_SUCCESS, "Article récupérée", article);
    }

    public ReponseService<ArticleVendu> vendreArticle(ArticleVendu article) {
        if (article == null ||
                article.getNomArticle() == null || article.getNomArticle().isEmpty() ||
                article.getDescription() == null || article.getDescription().isEmpty() ||
                article.getMiseAPrix() <= 0 ||
                article.getDateDebutEncheres() == null ||
                article.getDateFinEncheres() == null ||
                article.getEtatVente() == null || article.getEtatVente().isEmpty() ||
                article.getVendeur() == null ||
                article.getCategorie() == null) {

            return ReponseService.construireReponse(CD_ERR_BAD_REQUEST, "Certaines informations obligatoires sont manquantes ou invalides.", null);
        }

        if (article.getDateDebutEncheres().isAfter(article.getDateFinEncheres())) {
            return ReponseService.construireReponse(CD_ERR_BAD_REQUEST, "La date de début d’enchère doit être antérieure à la date de fin.", null);
        }

        if (article.getRetrait() == null && article.getVendeur() != null) {
            Retrait retraitParDefaut = new Retrait();
            retraitParDefaut.setRue(article.getVendeur().getRue());
            retraitParDefaut.setCodePostal(article.getVendeur().getCodePostal());
            retraitParDefaut.setVille(article.getVendeur().getVille());
            article.setRetrait(retraitParDefaut);
        }

        if (article.getPrixVente() == 0) {
            article.setPrixVente(article.getMiseAPrix());
        }

        try {
            articleVenduRepository.save(article); // Utilisation de save pour insérer ou mettre à jour
            return ReponseService.construireReponse(CD_CREATED, "L'article a été mis en vente avec succès.", article);
        } catch (Exception e) {
            return ReponseService.construireReponse(CD_ERR_INTERNAL, "Une erreur technique est survenue lors de l'enregistrement.", null);
        }
    }
}
