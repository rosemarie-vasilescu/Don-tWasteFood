package com.example.dontwastefood.Listeners;

import com.example.dontwastefood.Models.IngredientUPCResponse;
import com.example.dontwastefood.Models.IngredientsInfoResponse;

public interface IngredientsUPCListener {



        void didFetch(IngredientUPCResponse response, String message);
        void didError(String message);



}
