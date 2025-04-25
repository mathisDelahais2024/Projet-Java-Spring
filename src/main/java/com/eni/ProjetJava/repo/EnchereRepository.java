package com.eni.ProjetJava.repo;

import com.eni.ProjetJava.bo.Enchere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnchereRepository extends JpaRepository<Enchere, Long> {

}
