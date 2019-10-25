package com.user.festivalbizerte.Model;

import java.io.Serializable;

public class Programmes implements Serializable {

    private int id_prog;
    private String titre;
    private String description;
    private String date;
    private String horaire;
    private int prix;
    private int prix2;
    private int id_fest;

    public int getId_prog() {
        return id_prog;
    }

    public void setId_prog(int id_prog) {
        this.id_prog = id_prog;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getPrix2() {
        return prix2;
    }

    public void setPrix2(int prix2) {
        this.prix2 = prix2;
    }

    public int getId_fest() {
        return id_fest;
    }

    public void setId_fest(int id_fest) {
        this.id_fest = id_fest;
    }
}
