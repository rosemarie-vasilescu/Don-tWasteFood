package com.example.dontwastefood.Listeners;

import com.example.dontwastefood.Models.IngredientsInfoResponse;
import com.example.dontwastefood.Models.RecipeDetailsResponse;

import java.util.List;

public interface IngredientsInfoListener {

        void didFetch(IngredientsInfoResponse response, String message);
        void didError(String message);

}
