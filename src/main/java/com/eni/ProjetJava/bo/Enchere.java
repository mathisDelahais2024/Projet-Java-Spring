package com.eni.ProjetJava.bo;

import java.util.Date;

public class Enchere {
    private Date dateEnchere;
    private float montantEnchere;

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
}