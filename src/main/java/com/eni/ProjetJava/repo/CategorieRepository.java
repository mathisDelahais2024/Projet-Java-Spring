package com.eni.ProjetJava.repo;

import com.eni.ProjetJava.bo.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Categorie findByLibelle(String libelle);
}
