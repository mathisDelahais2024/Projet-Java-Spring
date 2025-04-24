package com.eni.ProjetJava.dao;

import com.eni.ProjetJava.bo.Enchere;

import java.util.List;

public interface IDAOEnchere {
    List<Enchere> selectAll();
    Enchere selectById(String id);
    void update(Enchere enchere);
}
