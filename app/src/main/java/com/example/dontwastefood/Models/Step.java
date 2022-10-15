package com.example.dontwastefood.Models;

import java.util.ArrayList;

public class Step {
    private int number;
    private String step;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Equipment> equipment;
    private Length length;

    public int getNumber() {
        return number;
    }

    public String getStep() {
        return step;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }

    public Length getLength() {
        return length;
    }
}
