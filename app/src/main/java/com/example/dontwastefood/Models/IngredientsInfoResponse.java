package com.example.dontwastefood.Models;

import java.util.ArrayList;

public class IngredientsInfoResponse {
    private int id;
    private String original;
    private String originalName;
    private String name;
    private double amount;
    private String unit;
    private String unitShort;
    private String unitLong;
    private ArrayList<String> possibleUnits;
    private EstimatedCost estimatedCost;
    private String consistency;
    private ArrayList<String> shoppingListUnits;
    private String aisle;
    private String image;
    private ArrayList<Object> meta;
    private Nutrition nutrition;
    private ArrayList<String> categoryPath;

    public int getId() {
        return id;
    }

    public String getOriginal() {
        return original;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public String getUnitShort() {
        return unitShort;
    }

    public String getUnitLong() {
        return unitLong;
    }

    public ArrayList<String> getPossibleUnits() {
        return possibleUnits;
    }

    public EstimatedCost getEstimatedCost() {
        return estimatedCost;
    }

    public String getConsistency() {
        return consistency;
    }

    public ArrayList<String> getShoppingListUnits() {
        return shoppingListUnits;
    }

    public String getAisle() {
        return aisle;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<Object> getMeta() {
        return meta;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public ArrayList<String> getCategoryPath() {
        return categoryPath;
    }
}
