package com.example.dontwastefood.Models;

import java.util.ArrayList;

public class RecipeDetailsResponse {

        private int id;
        private String title;
        private String image;
        private String imageType;
        private int servings;
        private int readyInMinutes;
        private String license;
        private String sourceName;
        private String sourceUrl;
        private String spoonacularSourceUrl;
        private int aggregateLikes;
        private double healthScore;
        private double spoonacularScore;
        private double pricePerServing;
        private ArrayList<Object> analyzedInstructions;
        private boolean cheap;
        private String creditsText;
        private ArrayList<Object> cuisines;
        private boolean dairyFree;
        private ArrayList<Object> diets;
        private String gaps;
        private boolean glutenFree;
        private String instructions;
        private boolean ketogenic;
        private boolean lowFodmap;
        private ArrayList<Object> occasions;
        private boolean sustainable;
        private boolean vegan;
        private boolean vegetarian;
        private boolean veryHealthy;
        private boolean veryPopular;
        private boolean whole30;
        private int weightWatcherSmartPoints;
        private ArrayList<String> dishTypes;
        private ArrayList<ExtendedIngredient> extendedIngredients;
        private String summary;
        private WinePairing winePairing;

        public int getId() {
                return id;
        }

        public String getTitle() {
                return title;
        }

        public String getImage() {
                return image;
        }

        public String getImageType() {
                return imageType;
        }

        public int getServings() {
                return servings;
        }

        public int getReadyInMinutes() {
                return readyInMinutes;
        }

        public String getLicense() {
                return license;
        }

        public String getSourceName() {
                return sourceName;
        }

        public String getSourceUrl() {
                return sourceUrl;
        }

        public String getSpoonacularSourceUrl() {
                return spoonacularSourceUrl;
        }

        public int getAggregateLikes() {
                return aggregateLikes;
        }

        public double getHealthScore() {
                return healthScore;
        }

        public double getSpoonacularScore() {
                return spoonacularScore;
        }

        public double getPricePerServing() {
                return pricePerServing;
        }

        public ArrayList<Object> getAnalyzedInstructions() {
                return analyzedInstructions;
        }

        public boolean isCheap() {
                return cheap;
        }

        public String getCreditsText() {
                return creditsText;
        }

        public ArrayList<Object> getCuisines() {
                return cuisines;
        }

        public boolean isDairyFree() {
                return dairyFree;
        }

        public ArrayList<Object> getDiets() {
                return diets;
        }

        public String getGaps() {
                return gaps;
        }

        public boolean isGlutenFree() {
                return glutenFree;
        }

        public String getInstructions() {
                return instructions;
        }

        public boolean isKetogenic() {
                return ketogenic;
        }

        public boolean isLowFodmap() {
                return lowFodmap;
        }

        public ArrayList<Object> getOccasions() {
                return occasions;
        }

        public boolean isSustainable() {
                return sustainable;
        }

        public boolean isVegan() {
                return vegan;
        }

        public boolean isVegetarian() {
                return vegetarian;
        }

        public boolean isVeryHealthy() {
                return veryHealthy;
        }

        public boolean isVeryPopular() {
                return veryPopular;
        }

        public boolean isWhole30() {
                return whole30;
        }

        public int getWeightWatcherSmartPoints() {
                return weightWatcherSmartPoints;
        }

        public ArrayList<String> getDishTypes() {
                return dishTypes;
        }

        public ArrayList<ExtendedIngredient> getExtendedIngredients() {
                return extendedIngredients;
        }

        public String getSummary() {
                return summary;
        }

        public WinePairing getWinePairing() {
                return winePairing;
        }
}
