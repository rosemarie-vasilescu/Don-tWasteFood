package com.example.dontwastefood.Models;

import java.util.ArrayList;

public class IngredientUPCResponse {
    private int id;
    private String title;
    private double price;
    private int likes;
    private ArrayList<String> badges;
    private ArrayList<String> importantBadges;
    private Nutrition nutrition;
    private Servings servings;
    private double spoonacularScore;
    private ArrayList<String> breadcrumbs;
    private String aisle;
    private Object description;
    private String image;
    private String imageType;
    private ArrayList<String> images;
    private Object generatedText;
    private String upc;
    private String brand;
    private ArrayList<Ingredient> ingredients;
    private int ingredientCount;
    private String ingredientList;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getLikes() {
        return likes;
    }

    public ArrayList<String> getBadges() {
        return badges;
    }

    public ArrayList<String> getImportantBadges() {
        return importantBadges;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public Servings getServings() {
        return servings;
    }

    public double getSpoonacularScore() {
        return spoonacularScore;
    }

    public ArrayList<String> getBreadcrumbs() {
        return breadcrumbs;
    }

    public String getAisle() {
        return aisle;
    }

    public Object getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public Object getGeneratedText() {
        return generatedText;
    }

    public String getUpc() {
        return upc;
    }

    public String getBrand() {
        return brand;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getIngredientCount() {
        return ingredientCount;
    }

    public String getIngredientList() {
        return ingredientList;
    }
}
