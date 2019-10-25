package com.user.festivalbizerte.Model;

import java.io.Serializable;

public class UserQuiz implements Serializable {

    private int id_user;
    private int score_jour;
    private String date_jouer;

    public UserQuiz(int id_user, int score_jour, String date_jouer) {
        this.id_user = id_user;
        this.score_jour = score_jour;
        this.date_jouer = date_jouer;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getScore_jour() {
        return score_jour;
    }

    public void setScore_jour(int score_jour) {
        this.score_jour = score_jour;
    }

    public String getDate_jouer() {
        return date_jouer;
    }

    public void setDate_jouer(String date_jouer) {
        this.date_jouer = date_jouer;
    }
}
