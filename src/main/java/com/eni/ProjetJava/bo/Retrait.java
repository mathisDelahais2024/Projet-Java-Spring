package com.eni.ProjetJava.bo;

import jakarta.persistence.*;

@Entity
public class Retrait {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noRetrait;

    @Column(nullable = false)
    private String rue;

    @Column(nullable = false)
    private String codePostal;

    @Column(nullable = false)
    private String ville;

    public Retrait() {}

    public Retrait(String rue, String codePostal, String ville) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public Long getNoRetrait() {
        return noRetrait;
    }

    public void setNoRetrait(Long noRetrait) {
        this.noRetrait = noRetrait;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

}
