package com.eni.ProjetJava.bo;

import java.time.LocalDate;

public class Enchere {
    private LocalDate dateEnchere;
    private float montantEnchere;
    private String etat;
    private ArticleVendu article;
    private Utilisateur encherisseur;
    private Utilisateur gagnant;
    private boolean retraitEffectue;



    public Enchere() {}

    public LocalDate getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDate dateEnchere) {
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

    public boolean isRetraitEffectue() {
        return retraitEffectue;
    }

    public void setRetraitEffectue(boolean retraitEffectue) {
        this.retraitEffectue = retraitEffectue;
    }

}