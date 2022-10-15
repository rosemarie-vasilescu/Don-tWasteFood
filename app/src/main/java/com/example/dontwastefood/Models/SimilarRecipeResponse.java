package com.example.dontwastefood.Models;

public class SimilarRecipeResponse {
    private int id;
    private String title;
    private String imageType;
    private int readyInMinutes;
    private int servings;
    private String sourceUrl;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageType() {
        return imageType;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }
}
