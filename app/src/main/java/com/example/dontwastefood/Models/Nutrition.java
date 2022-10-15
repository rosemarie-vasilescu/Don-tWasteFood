package com.example.dontwastefood.Models;

import java.util.ArrayList;

public class Nutrition {
    private ArrayList<Nutrient> nutrients;
    private ArrayList<Property> properties;
    private ArrayList<Flavonoid> flavonoids;
    private CaloricBreakdown caloricBreakdown;
    private WeightPerServing weightPerServing;

    public ArrayList<Nutrient> getNutrients() {
        return nutrients;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public ArrayList<Flavonoid> getFlavonoids() {
        return flavonoids;
    }

    public CaloricBreakdown getCaloricBreakdown() {
        return caloricBreakdown;
    }

    public WeightPerServing getWeightPerServing() {
        return weightPerServing;
    }
}
