package com.example.dontwastefood.Models;

import java.util.ArrayList;

public class IngredientsIdResponse {
    private ArrayList<IngredientId> results;
    private int offset;
    private int number;
    private int totalResults;

    public ArrayList<IngredientId> getResults() {
        return results;
    }

    public int getOffset() {
        return offset;
    }

    public int getNumber() {
        return number;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
