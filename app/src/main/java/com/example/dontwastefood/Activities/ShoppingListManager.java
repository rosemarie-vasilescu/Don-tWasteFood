package com.example.dontwastefood.Activities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.dontwastefood.Models.MyPantry;
import com.example.dontwastefood.Models.ShoppingList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ShoppingListManager {
    private static ShoppingListManager single_instance = null;
    private List<ShoppingList> shoppingList =new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private String shopList;
    private DatabaseHelper databaseHelper;
    private List<ShoppingList> list=new ArrayList<>();
    private boolean complete = false;
    private ShoppingListManager(){
        Log.i("PantryManager","in constructor" );
        this.firestore = FirebaseFirestore.getInstance();
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.auth = FirebaseAuth.getInstance();
        shopList = "";
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
//        this.pantry = showPantry();


    }
    public static ShoppingListManager getInstance(){
        if(single_instance == null){

            single_instance = new ShoppingListManager();
        }
        return single_instance;
    }

    public void addToShoppingList(String name,String quantity,Context context){
        this.firestore = FirebaseFirestore.getInstance();
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.auth = FirebaseAuth.getInstance();
        HashMap<String,Object> shoppingMap = new HashMap<>();
        String id = UUID.randomUUID().toString();
        shoppingMap.put("id",id);
        shoppingMap.put("name",name);
        shoppingMap.put("quantity",quantity);
        firestore.collection("AddToShoppingList").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").document(id).set(shoppingMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();}
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void showShoppingList(FirebaseCallback callback){
        this.shoppingList = new ArrayList<>();

        Log.i("in show","aici");
        firestore.collection("AddToShoppingList").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.i("PantryManager","on complete");
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){

                        ShoppingList shoppingListModel = documentSnapshot.toObject(ShoppingList.class);
                        shoppingList.add(shoppingListModel);
shopList = shopList + shoppingListModel.getName() + " , ";


                    }
Log.i("in manager",shoppingList.toString());
                    callback.onResponse(shoppingList,shopList);
                    shopList = "";
//                    setPantry(pantry);

                    //Toast.makeText(PantryActivity.this, ingredients, Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.w("ShoppingListManager","Error");
                }
            }
        });


    }

    public void getId(String name,IdCallback callback){
        firestore.collection("AddToShoppingList").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").whereEqualTo("name",name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){

                        ShoppingList shoppingListModel = documentSnapshot.toObject(ShoppingList.class);
                    shoppingListModel.getId();
                    callback.onResponse(shoppingListModel.getId());
                        Log.i("getId",shoppingListModel.getId());
                    }

                }
                else
                {
                    Log.w("ShoppingListManager","Error");
                }
            }

        });
    }

//    public List<MyPantry> setPantry(List<MyPantry> pantry){
////        Log.i("PantryManager",pantry.name);
//        list.addAll(pantry);
//        Log.i("PantryManager","in set" );
//        this.pantry = list;
//        Log.i("PantryManager",this.pantry.toString());
//        return list;
////        this.pantry = list;
//    }
//    public List<MyPantry> getPantry(){
//        Log.i("PantryManager"," in get");
//        Log.i("PantryManager",this.pantry.toString());
//        showPantry();
//
//        return this.pantry;
//    }



    public void updateShoppingList(String id, String quantity){


        firestore.collection("AddToShoppingList").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").document(id).update("quantity",quantity).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                }
                else{

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
    public void deleteFromShoppingList(String id){
        Log.i("delete","delete");
        firestore.collection("AddToShoppingList").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("on_complete delete","delete");
                if(task.isSuccessful()){

                }
                else {

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
    public interface FirebaseCallback{
        void onResponse(List<ShoppingList> shoppingList,String shopList);
    }
    public interface IdCallback{
        void onResponse(String id);
    }
}
