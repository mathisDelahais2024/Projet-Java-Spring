package com.eni.ProjetJava.repo;

import com.eni.ProjetJava.bo.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    // Méthode pour rechercher un utilisateur par son email
    Utilisateur findByEmail(String email);
}
