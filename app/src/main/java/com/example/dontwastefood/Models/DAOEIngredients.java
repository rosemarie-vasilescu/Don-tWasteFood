package com.example.dontwastefood.Models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOEIngredients {
    private DatabaseReference databaseReference;
    public DAOEIngredients(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(MyPantry.class.getSimpleName());
    }
}
