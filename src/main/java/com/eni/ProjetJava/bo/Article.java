package com.eni.ProjetJava.bo;

import java.time.LocalDate;

public class Article {
    private String nom;
    private int prix;
    private int classement;
    private LocalDate dateFin;
    private String adresseRetrait;
    private String vendeur;
    private String imageUrl;

    // Constructeur
    public Article(String nom, int prix, int classement, LocalDate dateFin, String adresseRetrait, String vendeur, String imageUrl) {
        this.nom = nom;
        this.prix = prix;
        this.classement = classement;
        this.dateFin = dateFin;
        this.adresseRetrait = adresseRetrait;
        this.vendeur = vendeur;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getNom() { return nom; }
    public int getPrix() { return prix; }
    public int getClassement() { return classement; }
    public LocalDate getDateFin() { return dateFin; }
    public String getAdresseRetrait() { return adresseRetrait; }
    public String getVendeur() { return vendeur; }
    public String getImageUrl() { return imageUrl; }
}
