package com.example.dontwastefood.Listeners;

import com.example.dontwastefood.Models.IngredientsIdResponse;

public interface IngredientsIdResponseListener {
    void didFetch(IngredientsIdResponse response, String message);
    void didError(String message);
}