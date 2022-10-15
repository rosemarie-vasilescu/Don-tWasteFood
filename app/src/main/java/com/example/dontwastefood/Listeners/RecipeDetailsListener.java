package com.example.dontwastefood.Listeners;

import com.example.dontwastefood.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
