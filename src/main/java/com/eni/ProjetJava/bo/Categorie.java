package com.eni.ProjetJava.bo;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noCategorie;

    @Column(nullable = false, length = 50)
    private String libelle;

    @OneToMany(mappedBy = "categorie")
    private List<ArticleVendu> articles;

    public Categorie() {}

    public List<ArticleVendu> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleVendu> articles) {
        this.articles = articles;
    }

    public Long getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(Long noCategorie) {
        this.noCategorie = noCategorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}
