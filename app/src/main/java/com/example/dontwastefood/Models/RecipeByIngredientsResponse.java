package com.example.dontwastefood.Models;

import java.util.ArrayList;

public class RecipeByIngredientsResponse {
    private int id;
    private String image;
    private String imageType;
    private int likes;
    private int missedIngredientCount;
    private ArrayList<MissedIngredient> missedIngredients;
    private String title;
    private ArrayList<UnusedIngredient> unusedIngredients;
    private int usedIngredientCount;
    private ArrayList<UsedIngredient> usedIngredients;

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public int getLikes() {
        return likes;
    }

    public int getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public ArrayList<MissedIngredient> getMissedIngredients() {
        return missedIngredients;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<UnusedIngredient> getUnusedIngredients() {
        return unusedIngredients;
    }

    public int getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public ArrayList<UsedIngredient> getUsedIngredients() {
        return usedIngredients;
    }
}
