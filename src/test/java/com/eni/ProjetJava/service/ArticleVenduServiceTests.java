package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.ArticleVendu;
import com.eni.ProjetJava.bo.Categorie;
import com.eni.ProjetJava.bo.Utilisateur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static com.eni.ProjetJava.service.ConstanteService.CD_CREATED;
import static com.eni.ProjetJava.service.ConstanteService.CD_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ArticleVenduServiceTests {

    @Autowired
    ArticleVenduService articleVenduService;

    @Test
    void Test_ArticleVendu_getAll() {
        ReponseService<List<ArticleVendu>> reponse = articleVenduService.getAll();

        assertNotNull(reponse, "La réponse ne doit pas être nulle");
        assertEquals(CD_SUCCESS, reponse.getCode(), "Le code de la réponse devrait être 200");
        assertEquals("Liste des articles récupérée", reponse.getMessage(), "Le message devrait être 'Liste des articles récupérée'");
        assertNotNull(reponse.getData(), "La liste des articles ne doit pas être nulle");
    }

    @Test
    void Test_ArticleVendu_getByNoArticle() {
        String noArticle = "1";

        ReponseService<ArticleVendu> reponse = articleVenduService.getByNoArticle(noArticle);

        assertNotNull(reponse, "La réponse ne doit pas être nulle");
        assertEquals(CD_SUCCESS, reponse.getCode(), "Le code de la réponse devrait être 200");
        assertNotNull(reponse.getData(), "L'article récupéré ne doit pas être nul");
        assertEquals(noArticle, reponse.getData().getNoArticle(), "L'ID de l'article devrait être '1'");
    }

    @Test
    void Test_ArticleVendu_vendreArticle() {

        Categorie categorie1 = new Categorie("1", "test");

        ArticleVendu article = new ArticleVendu();
        article.setNomArticle("Article Test");
        article.setDescription("Description de l'article Test");
        article.setMiseAPrix(100);
        article.setPrixVente(150);
        article.setDateDebutEncheres(LocalDate.now());
        article.setDateFinEncheres(LocalDate.now().plusDays(2));
        article.setEtatVente("Disponible");
        article.setVendeur(new Utilisateur());
        article.setCategorie(categorie1);

        ReponseService<ArticleVendu> reponse = articleVenduService.vendreArticle(article);

        assertNotNull(reponse, "La réponse ne doit pas être nulle");
        assertEquals(CD_CREATED, reponse.getCode(), "Le code de la réponse devrait être 201");
        assertEquals("L'article a été mis en vente avec succès.", reponse.getMessage(), "Le message devrait être 'L'article a été mis en vente avec succès.'");
        assertNotNull(reponse.getData(), "L'article mis en vente ne doit pas être nul");
    }
}
