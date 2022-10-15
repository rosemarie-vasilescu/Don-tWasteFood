package com.example.dontwastefood.Models;

import java.util.ArrayList;

public class UsedIngredient {
    private String aisle;
    private double amount;
    private int id;
    private String image;
    private ArrayList<Object> meta;
    private String name;
    private String original;
    private String originalName;
    private String unit;
    private String unitLong;
    private String unitShort;

    public String getAisle() {
        return aisle;
    }

    public double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<Object> getMeta() {
        return meta;
    }

    public String getName() {
        return name;
    }

    public String getOriginal() {
        return original;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getUnit() {
        return unit;
    }

    public String getUnitLong() {
        return unitLong;
    }

    public String getUnitShort() {
        return unitShort;
    }
}
