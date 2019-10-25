package com.user.festivalbizerte.Model;

import java.io.Serializable;

public class Sponsors implements Serializable {

    private int id_sp;
    private String nom;
    private String description;
    private String genre;
    private String website;
    private String logo;
    private int id_fest;

    public int getId_sp() {
        return id_sp;
    }

    public void setId_sp(int id_sp) {
        this.id_sp = id_sp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getId_fest() {
        return id_fest;
    }

    public void setId_fest(int id_fest) {
        this.id_fest = id_fest;
    }
}
