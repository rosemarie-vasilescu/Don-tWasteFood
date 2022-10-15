package com.example.dontwastefood.Listeners;

import com.example.dontwastefood.Models.IngredientsResponse;
import com.example.dontwastefood.Models.RecipeByIngredientsResponse;

import java.util.List;

public interface RecipeByIngredientsListener {
    void didFetch(List<RecipeByIngredientsResponse> response, String message);
    void didError(String message);
}
