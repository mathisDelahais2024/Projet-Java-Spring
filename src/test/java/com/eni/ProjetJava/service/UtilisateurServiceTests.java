package com.eni.ProjetJava.service;

import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.dao.IDAOUtilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.eni.ProjetJava.service.ConstanteService.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UtilisateurServiceTests {
    @Mock
    private IDAOUtilisateur utilisateurDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInscrireUtilisateur_MotsDePasseNonCorrespondants() {
        String pseudo = "user1";
        String nom = "Nom";
        String prenom = "Prenom";
        String email = "user1@example.com";
        long telephone = 123456789L;
        String rue = "123 Rue Exemple";
        String codePostal = "75000";
        String ville = "Paris";
        String motDePasse = "password123";
        String confirmMotDePasse = "password124";

        String result = utilisateurService.inscrireUtilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, confirmMotDePasse);

        assertNotNull(result, "Le résultat de l'inscription ne doit pas être nul");
        assertEquals(ConstanteService.CD_ERR_BAD_REQUEST, result, "Le message d'erreur devrait être le code d'erreur 400 pour mots de passe non correspondants");
    }

    @Test
    public void testInscrireUtilisateur_EmailDejaExistant() {
        String pseudo = "user1";
        String nom = "Nom";
        String prenom = "Prenom";
        String email = "user1@example.com";
        long telephone = 123456789L;
        String rue = "123 Rue Exemple";
        String codePostal = "75000";
        String ville = "Paris";
        String motDePasse = "password123";
        String confirmMotDePasse = "password123";

        when(utilisateurDao.findByEmail(email)).thenReturn(new Utilisateur());

        String result = utilisateurService.inscrireUtilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, confirmMotDePasse);

        assertNotNull(result, "Le résultat de l'inscription ne doit pas être nul");
        assertEquals(ConstanteService.CD_ERR_CONFLICT, result, "Le message d'erreur devrait être le code d'erreur 409 pour email déjà existant");
    }

    @Test
    public void testInscrireUtilisateur_Success() {
        // Préparer les données
        String pseudo = "user1";
        String nom = "Nom";
        String prenom = "Prenom";
        String email = "user1@example.com";
        long telephone = 123456789L;
        String rue = "123 Rue Exemple";
        String codePostal = "75000";
        String ville = "Paris";
        String motDePasse = "password123";
        String confirmMotDePasse = "password123";

        when(utilisateurDao.findByEmail(email)).thenReturn(null);

        when(passwordEncoder.encode(motDePasse)).thenReturn("encodedPassword");

        String result = utilisateurService.inscrireUtilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, confirmMotDePasse);

        assertNull(result, "La réponse ne doit pas contenir d'erreur");

        verify(utilisateurDao).save(any(Utilisateur.class));
    }

    @Test
    void testSupprimerUtilisateurParEmail_UtilisateurNonTrouve() {
        String email = "inexistant@exemple.com";
        when(utilisateurDao.findByEmail(email)).thenReturn(null);

        String resultat = utilisateurService.supprimerUtilisateurParEmail(email);

        assertEquals(CD_ERR_NOT_FOUND, resultat, "Le code d'erreur devrait être 404 si l'utilisateur n'existe pas.");
    }

    @Test
    void testSupprimerUtilisateurParEmail_Succes() {
        String email = "utilisateur@exemple.com";
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);

        when(utilisateurDao.findByEmail(email)).thenReturn(utilisateur);

        String resultat = utilisateurService.supprimerUtilisateurParEmail(email);

        assertEquals(CD_SUCCESS, resultat, "Le code retour attendu est 200 pour une suppression réussie.");
        verify(utilisateurDao).deleteByEmail(email);
    }
}
