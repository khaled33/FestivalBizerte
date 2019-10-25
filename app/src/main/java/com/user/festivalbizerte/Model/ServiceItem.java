package com.user.festivalbizerte.Model;

public class ServiceItem {


   private String NomService;
   private int ImageService;

    public ServiceItem(String nomService, int imageService) {
        NomService = nomService;
        ImageService = imageService;
    }
    public ServiceItem() {

    }
    public String getNomService() {
        return NomService;
    }

    public void setNomService(String nomService) {
        NomService = nomService;
    }

    public int getImageService() {
        return ImageService;
    }

    public void setImageService(int imageService) {
        ImageService = imageService;
    }
}
