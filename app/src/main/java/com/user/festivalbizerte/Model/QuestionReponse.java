package com.user.festivalbizerte.Model;

import java.io.Serializable;
import java.util.List;

public class QuestionReponse implements Serializable {

    private int id_qt;
    private int id_quiz;
    private String body;
    private List<Reponses> listeRep;

    public int getId_qt() {
        return id_qt;
    }

    public void setId_qt(int id_qt) {
        this.id_qt = id_qt;
    }

    public int getId_quiz() {
        return id_quiz;
    }

    public void setId_quiz(int id_quiz) {
        this.id_quiz = id_quiz;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Reponses> getListeRep() {
        return listeRep;
    }

    public void setListeRep(List<Reponses> listeRep) {
        this.listeRep = listeRep;
    }
}
