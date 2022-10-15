package com.example.dontwastefood.Models;

public class Nutrient {
    private String name;
    private double amount;
    private String unit;
    private double percentOfDailyNeeds;

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public double getPercentOfDailyNeeds() {
        return percentOfDailyNeeds;
    }
}
