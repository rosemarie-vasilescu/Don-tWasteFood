package com.example.dontwastefood.Models;

import java.util.ArrayList;

public class Recipe {
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private boolean veryHealthy;
    private boolean cheap;
    private boolean veryPopular;
    private boolean sustainable;
    private int weightWatcherSmartPoints;
    private String gaps;
    private boolean lowFodmap;
    private int aggregateLikes;
    private double spoonacularScore;
    private double healthScore;
    private String creditsText;
    private String license;
    private String sourceName;
    private double pricePerServing;
    private ArrayList<ExtendedIngredient> extendedIngredients;
    private int id;
    private String title;
    private int readyInMinutes;
    private int servings;
    private String sourceUrl;
    private String image;
    private String imageType;
    private String summary;
    private ArrayList<Object> cuisines;
    private ArrayList<String> dishTypes;
    private ArrayList<String> diets;
    private ArrayList<Object> occasions;
    private String instructions;
    private ArrayList<AnalyzedInstruction> analyzedInstructions;
    private Object originalId;
    private String spoonacularSourceUrl;

    public boolean isVegetarian() {
        return vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public boolean isVeryHealthy() {
        return veryHealthy;
    }

    public boolean isCheap() {
        return cheap;
    }

    public boolean isVeryPopular() {
        return veryPopular;
    }

    public boolean isSustainable() {
        return sustainable;
    }

    public int getWeightWatcherSmartPoints() {
        return weightWatcherSmartPoints;
    }

    public String getGaps() {
        return gaps;
    }

    public boolean isLowFodmap() {
        return lowFodmap;
    }

    public int getAggregateLikes() {
        return aggregateLikes;
    }

    public double getSpoonacularScore() {
        return spoonacularScore;
    }

    public double getHealthScore() {
        return healthScore;
    }

    public String getCreditsText() {
        return creditsText;
    }

    public String getLicense() {
        return license;
    }

    public String getSourceName() {
        return sourceName;
    }

    public double getPricePerServing() {
        return pricePerServing;
    }

    public ArrayList<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
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

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public String getSummary() {
        return summary;
    }

    public ArrayList<Object> getCuisines() {
        return cuisines;
    }

    public ArrayList<String> getDishTypes() {
        return dishTypes;
    }

    public ArrayList<String> getDiets() {
        return diets;
    }

    public ArrayList<Object> getOccasions() {
        return occasions;
    }

    public String getInstructions() {
        return instructions;
    }

    public ArrayList<AnalyzedInstruction> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public Object getOriginalId() {
        return originalId;
    }

    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }
}
