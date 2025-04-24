package com.eni.ProjetJava.bo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ArticleVendu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noArticle;

    @Column(nullable = false)
    private String nomArticle;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDate dateDebutEncheres;

    @Column(nullable = false)
    private LocalDate dateFinEncheres;

    @Column(nullable = false)
    private float miseAPrix;

    private float prixVente;

    private String etatVente;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "vendeur_id")
    private Utilisateur vendeur;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "retrait_id")
    private Retrait retrait;

    public ArticleVendu() {}

    public ArticleVendu(Long noArticle, String nomArticle, String description,
                        LocalDate dateDebutEncheres, LocalDate dateFinEncheres,
                        float miseAPrix, float prixVente, String etatVente,
                        Categorie categorie, Utilisateur vendeur, Retrait retrait) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.categorie = categorie;
        this.vendeur = vendeur;
        this.retrait = retrait;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public Long getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(Long noArticle) {
        this.noArticle = noArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDate getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDate dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public float getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(float miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    public float getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(float prixVente) {
        this.prixVente = prixVente;
    }

    public String getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(String etatVente) {
        this.etatVente = etatVente;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Utilisateur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Utilisateur vendeur) {
        this.vendeur = vendeur;
    }

    public Retrait getRetrait() {
        return retrait;
    }

    public void setRetrait(Retrait retrait) {
        this.retrait = retrait;
    }

}
