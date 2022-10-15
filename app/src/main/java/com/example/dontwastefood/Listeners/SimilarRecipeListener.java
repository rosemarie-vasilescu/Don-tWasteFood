package com.example.dontwastefood.Listeners;

import com.example.dontwastefood.Models.SimilarRecipeResponse;

import java.util.List;

public interface SimilarRecipeListener {
    void didFetch(List<SimilarRecipeResponse> response, String message);
    void didError(String message);

}
