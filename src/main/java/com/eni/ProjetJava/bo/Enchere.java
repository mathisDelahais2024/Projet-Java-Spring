package com.eni.ProjetJava.bo;

import java.util.Date;

public class Enchere {
    private Date dateEnchere;
    private float montantEnchere;
    private String etat;
    private ArticleVendu article;
    private Utilisateur encherisseur;
    private Utilisateur gagnant;


    public Enchere() {}

    public Date getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(Date dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public float getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(float montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public ArticleVendu getArticle() {
        return article;
    }

    public void setArticle(ArticleVendu article) {
        this.article = article;
    }

    public Utilisateur getEncherisseur() {
        return encherisseur;
    }

    public void setEncherisseur(Utilisateur encherisseur) {
        this.encherisseur = encherisseur;
    }

    public Utilisateur getGagnant() {
        return gagnant;
    }

    public void setGagnant(Utilisateur gagnant) {
        this.gagnant = gagnant;
    }
}