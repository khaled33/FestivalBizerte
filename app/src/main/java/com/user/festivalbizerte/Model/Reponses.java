package com.user.festivalbizerte.Model;

import java.io.Serializable;

public class Reponses implements Serializable {

    private int id_rep;
    private int id_qt;
    private String body;
    private int is_valide;
    private int noteRep;

    public int getId_rep() {
        return id_rep;
    }

    public void setId_rep(int id_rep) {
        this.id_rep = id_rep;
    }

    public int getId_qt() {
        return id_qt;
    }

    public void setId_qt(int id_qt) {
        this.id_qt = id_qt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getIs_valide() {
        return is_valide;
    }

    public void setIs_valide(int is_valide) {
        this.is_valide = is_valide;
    }

    public int getNoteRep() {
        return noteRep;
    }

    public void setNoteRep(int noteRep) {
        this.noteRep = noteRep;
    }
}
