package com.example.dontwastefood.Listeners;

import com.example.dontwastefood.Models.Ingredient;
import com.example.dontwastefood.Models.IngredientsResponse;
import com.example.dontwastefood.Models.InstructionsResponse;

import java.util.List;

public interface IngredientsResponseListener {
    void didFetch(List<IngredientsResponse> response, String message);
    void didError(String message);
}
