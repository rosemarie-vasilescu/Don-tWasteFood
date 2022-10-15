package com.example.dontwastefood.Models;

import java.util.ArrayList;

public class WinePairing {
    private ArrayList<String> pairedWines;
    private String pairingText;
    private ArrayList<ProductMatch> productMatches;

    public ArrayList<String> getPairedWines() {
        return pairedWines;
    }

    public String getPairingText() {
        return pairingText;
    }

    public ArrayList<ProductMatch> getProductMatches() {
        return productMatches;
    }
}
