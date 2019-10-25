package com.user.festivalbizerte.Model;

import java.io.Serializable;

public class ArtisteProgramee implements Serializable {

    private int id_prog;
    private String datePassage;
    private String heurePassage;
    private int ordre;
    private Artistes artiste;

    public ArtisteProgramee() {
    }

    public int getId_prog() {
        return id_prog;
    }

    public void setId_prog(int id_prog) {
        this.id_prog = id_prog;
    }

    public String getDatePassage() {
        return datePassage;
    }

    public void setDatePassage(String datePassage) {
        this.datePassage = datePassage;
    }

    public String getHeurePassage() {
        return heurePassage;
    }

    public void setHeurePassage(String heurePassage) {
        this.heurePassage = heurePassage;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public Artistes getArtiste() {
        return artiste;
    }

    public void setArtiste(Artistes artiste) {
        this.artiste = artiste;
    }
}
