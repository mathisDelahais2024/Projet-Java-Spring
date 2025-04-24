package com.eni.ProjetJava.bo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Enchere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noEnchere;

    @Column(nullable = false)
    private LocalDate dateEnchere;

    @Column(nullable = false)
    private float montantEnchere;

    private String etat;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id")
    private ArticleVendu article;

    @ManyToOne(optional = false)
    @JoinColumn(name = "encherisseur_id")
    private Utilisateur encherisseur;

    @ManyToOne
    @JoinColumn(name = "gagnant_id")
    private Utilisateur gagnant;


    public Enchere() {}

    public Enchere(LocalDate dateEnchere, float montantEnchere, String etat, ArticleVendu article, Utilisateur encherisseur, Utilisateur gagnant) {
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
        this.etat = etat;
        this.article = article;
        this.encherisseur = encherisseur;
        this.gagnant = gagnant;
    }

    public LocalDate getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDate dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public Long getNoEnchere() {
        return noEnchere;
    }

    public void setNoEnchere(Long noEnchere) {
        this.noEnchere = noEnchere;
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