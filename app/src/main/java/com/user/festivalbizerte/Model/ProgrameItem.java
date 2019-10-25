package com.user.festivalbizerte.Model;

public class ProgrameItem {


   private String Title;
   private String Prix;
   private String Jour;
   private String Mois;
   private String Horaire;
   private String Description;

    public ProgrameItem() {

    }
    public ProgrameItem(String title, String prix, String Jour, String Mois,String horaire,String Description) {
        this.Title = title;
        this.Prix = prix;
        this.Jour = Jour;
        this.Mois = Mois;
        this.Horaire = horaire;
        this.Description = Description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPrix() {
        return Prix;
    }

    public void setPrix(String prix) {
        Prix = prix;
    }

    public String getJour() {
        return Jour;
    }

    public void setJour(String jour) {
        Jour = jour;
    }

    public String getMois() {
        return Mois;
    }

    public void setMois(String mois) {
        Mois = mois;
    }

    public String getHoraire() {
        return Horaire;
    }

    public void setHoraire(String horaire) {
        Horaire = horaire;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
