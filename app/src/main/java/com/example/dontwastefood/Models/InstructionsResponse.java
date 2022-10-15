package com.example.dontwastefood.Models;

import java.util.ArrayList;

public class InstructionsResponse {
    private String name;
    private ArrayList<Step> steps;

    public String getName() {
        return name;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }
}
