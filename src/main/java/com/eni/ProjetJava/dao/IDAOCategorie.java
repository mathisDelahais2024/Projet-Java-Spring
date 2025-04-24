package com.eni.ProjetJava.dao;

import com.eni.ProjetJava.bo.Categorie;

import java.util.List;

public interface IDAOCategorie {
    List<Categorie> selectAll();
}
