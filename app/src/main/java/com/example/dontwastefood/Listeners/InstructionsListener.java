package com.example.dontwastefood.Listeners;

import com.example.dontwastefood.Models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> response, String message);
    void didError(String message);
}
