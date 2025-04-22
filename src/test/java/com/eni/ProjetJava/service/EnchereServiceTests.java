package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.ArticleVendu;
import com.eni.ProjetJava.bo.Categorie;
import com.eni.ProjetJava.bo.Enchere;
import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.dao.IDAOEnchere;
import com.eni.ProjetJava.dao.IDAOUtilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.ArrayList;

import static com.eni.ProjetJava.service.ConstanteService.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnchereServiceTests {

    @Mock
    private IDAOEnchere daoEnchere;

    @Mock
    private IDAOUtilisateur daoUtilisateur;

    @InjectMocks
    private EnchereService enchereService;

    private Enchere enchere1;
    private Enchere enchere2;
    private Enchere enchere3;
    private Enchere enchere4;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Categorie categorie1 = new Categorie("1", "Électronique");
        Categorie categorie2 = new Categorie("2", "Meubles");

        ArticleVendu article1 = new ArticleVendu("1", "Smartphone", "Un smartphone haut de gamme", null, null, 100, 150, "bon", categorie1);
        ArticleVendu article2 = new ArticleVendu("2", "Canapé", "Canapé confortable", null, null, 50, 70, "bon", categorie2);
        ArticleVendu article3 = new ArticleVendu("3", "Ordinateur portable", "Ordinateur puissant pour le jeu", null, null, 300, 500, "bon", categorie1);
        ArticleVendu article4 = new ArticleVendu("4", "Canapé en cuir", "Canapé en cuir confortable", null, null, 50, 70, "bon", categorie2);

        enchere1 = new Enchere();
        enchere1.setArticle(article1);
        enchere1.setMontantEnchere(120);

        enchere2 = new Enchere();
        enchere2.setArticle(article2);
        enchere2.setMontantEnchere(60);

        enchere3 = new Enchere();
        enchere3.setArticle(article3);
        enchere3.setMontantEnchere(400);

        enchere4 = new Enchere();
        enchere4.setArticle(article4);
        enchere4.setMontantEnchere(80);
    }

    @Test
    public void testGetAllEncheres_Success() {
        List<Enchere> encheres = new ArrayList<>();
        encheres.add(enchere1);
        encheres.add(enchere2);
        encheres.add(enchere3);
        encheres.add(enchere4);

        when(daoEnchere.selectAll()).thenReturn(encheres);

        ReponseService<List<Enchere>> result = enchereService.getAll();

        assertNotNull(result, "Le résultat ne doit pas être nul");
        assertEquals(CD_SUCCESS, result.getCode(), "Le code de retour devrait être 200");
        assertEquals(4, result.getData().size(), "Il devrait y avoir 4 enchères.");
    }

    @Test
    public void testGetEnchereById_Success() {
        when(daoEnchere.selectById(1)).thenReturn(enchere1);

        ReponseService<Enchere> result = enchereService.getById(1);

        assertNotNull(result, "Le résultat ne doit pas être nul");
        assertEquals(CD_SUCCESS, result.getCode(), "Le code de retour devrait être 200");
        assertEquals(enchere1, result.getData(), "L'enchère retournée doit être celle avec l'ID 1");
    }

    @Test
    public void testGetEnchereById_NotFound() {
        when(daoEnchere.selectById(99)).thenReturn(null);

        ReponseService<Enchere> result = enchereService.getById(99);

        assertNotNull(result, "Le résultat ne doit pas être nul");
        assertEquals(CD_ERR_NOT_FOUND, result.getCode(), "Le code de retour devrait être 404");
    }

    @Test
    public void testGetEncheresPubliques_FiltrageParCategorie() {
        List<Enchere> encheres = new ArrayList<>();
        encheres.add(enchere1);
        encheres.add(enchere2);
        encheres.add(enchere3);
        encheres.add(enchere4);

        String categorieId = "Électronique";
        String nomArticle = "";

        when(daoEnchere.selectAll()).thenReturn(encheres);

        ReponseService<List<Enchere>> result = enchereService.getEncheresPubliques(categorieId, nomArticle);

        assertNotNull(result, "Le résultat ne doit pas être nul");
        assertEquals(CD_SUCCESS, result.getCode(), "Le code de retour devrait être 200");
        assertEquals(2, result.getData().size(), "Il devrait y avoir 2 enchères correspondant à la catégorie Électronique");
    }

    @Test
    public void testGetEncheresPubliques_FiltrageParNomArticle() {
        List<Enchere> encheres = new ArrayList<>();
        encheres.add(enchere1);
        encheres.add(enchere2);
        encheres.add(enchere3);
        encheres.add(enchere4);

        String categorieId = "";
        String nomArticle = "Smartphone";

        when(daoEnchere.selectAll()).thenReturn(encheres);

        ReponseService<List<Enchere>> result = enchereService.getEncheresPubliques(categorieId, nomArticle);

        assertNotNull(result, "Le résultat ne doit pas être nul");
        assertEquals(CD_SUCCESS, result.getCode(), "Le code de retour devrait être 200");
        assertEquals(1, result.getData().size(), "Il devrait y avoir 1 enchère correspondant au nom 'Smartphone'");
    }

    @Test
    public void testGetEncheresPubliques_FiltrageParNomEtCategorie() {
        List<Enchere> encheres = new ArrayList<>();
        encheres.add(enchere1);
        encheres.add(enchere2);
        encheres.add(enchere3);
        encheres.add(enchere4);

        String categorieId = "Électronique";
        String nomArticle = "Ordinateur portable";

        when(daoEnchere.selectAll()).thenReturn(encheres);

        ReponseService<List<Enchere>> result = enchereService.getEncheresPubliques(categorieId, nomArticle);

        assertNotNull(result, "Le résultat ne doit pas être nul");
        assertEquals(CD_SUCCESS, result.getCode(), "Le code de retour devrait être 200");
        assertEquals(1, result.getData().size(), "Il devrait y avoir 1 enchère correspondant à 'Ordinateur portable' dans la catégorie 'Électronique'");
    }

    @Test
    public void testGetEncheresPubliques_NoResults() {
        List<Enchere> encheres = new ArrayList<>();
        encheres.add(enchere1);
        encheres.add(enchere2);
        encheres.add(enchere3);
        encheres.add(enchere4);

        String categorieId = "Maison";
        String nomArticle = "Tablette";

        when(daoEnchere.selectAll()).thenReturn(encheres);

        ReponseService<List<Enchere>> result = enchereService.getEncheresPubliques(categorieId, nomArticle);

        assertNotNull(result, "Le résultat ne doit pas être nul");
        assertEquals(CD_SUCCESS, result.getCode(), "Le code de retour devrait être 200");
        assertTrue(result.getData().isEmpty(), "La liste des enchères filtrées devrait être vide.");
    }

    @Test
    public void testGetEncheresParUtilisateur_Success() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNoUtilisateur("u123");
        utilisateur.setEmail("user@example.com");
        enchere1.setEncherisseur(utilisateur);

        Utilisateur utilisateur2 = new Utilisateur();
        utilisateur2.setNoUtilisateur("u124");
        utilisateur2.setEmail("autre@eni.fr");
        enchere2.setEncherisseur(utilisateur2);

        when(daoUtilisateur.findByEmail("user@example.com")).thenReturn(utilisateur);
        when(daoEnchere.selectAll()).thenReturn(List.of(enchere1, enchere2));

        var result = enchereService.getEncheresParUtilisateur("user@example.com");

        assertNotNull(result, "Le résultat ne doit pas être nul");
        assertEquals(CD_SUCCESS, result.getCode(), "Le code de retour devrait être 200");
        assertEquals(1, result.getData().size(), "Il devrait y avoir 1 enchère pour cet utilisateur");
        assertEquals(utilisateur, result.getData().get(0).getEncherisseur());
    }


    @Test
    public void testGetEncheresParUtilisateur_NotFound() {
        when(daoUtilisateur.findByEmail("unknown@example.com")).thenReturn(null);

        var result = enchereService.getEncheresParUtilisateur("unknown@example.com");

        assertNotNull(result, "Le résultat ne doit pas être nul");
        assertEquals(CD_ERR_NOT_FOUND, result.getCode(), "Le code de retour devrait être 404");
    }

    @Test
    public void testGetEncheresGagnees_Success() {
        Utilisateur gagnant = new Utilisateur();
        gagnant.setEmail("winner@example.com");
        enchere1.setGagnant(gagnant);

        Utilisateur fauxGagnant = new Utilisateur();
        fauxGagnant.setEmail("autre@eni.fr");
        enchere2.setGagnant(fauxGagnant);

        when(daoUtilisateur.findByEmail("winner@example.com")).thenReturn(gagnant);
        when(daoEnchere.selectAll()).thenReturn(List.of(enchere1, enchere2));

        var result = enchereService.getEncheresGagnees("winner@example.com");

        assertNotNull(result, "Le résultat ne doit pas être nul");
        assertEquals(CD_SUCCESS, result.getCode(), "Le code de retour devrait être 200");
        assertEquals(1, result.getData().size());
        assertEquals("winner@example.com", result.getData().get(0).getGagnant().getEmail());
    }

    @Test
    public void testGetEncheresGagnees_UtilisateurNonTrouve() {
        when(daoUtilisateur.findByEmail("unknown@example.com")).thenReturn(null);

        var result = enchereService.getEncheresGagnees("unknown@example.com");

        assertNotNull(result, "Le résultat ne doit pas être nul");
        assertEquals(CD_ERR_NOT_FOUND, result.getCode(), "Le code de retour devrait être 404");
    }

    @Test
    public void testGetEncheresParEtat_Filtre() {
        enchere1.setEtat("en_cours");
        enchere2.setEtat("terminee");
        enchere3.setEtat("en_cours");

        when(daoEnchere.selectAll()).thenReturn(List.of(enchere1, enchere2, enchere3));

        var result = enchereService.getEncheresParEtat("en_cours");

        assertNotNull(result, "Le résultat ne doit pas être nul");
        assertEquals(CD_SUCCESS, result.getCode(), "Le code de retour devrait être 200");
        assertEquals(2, result.getData().size());
        assertTrue(result.getData().stream().allMatch(e -> "en_cours".equals(e.getEtat())));
    }
}
