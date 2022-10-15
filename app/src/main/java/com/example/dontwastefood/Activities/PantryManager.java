package com.example.dontwastefood.Activities;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dontwastefood.Listeners.IngredientsIdResponseListener;
import com.example.dontwastefood.Listeners.IngredientsInfoListener;
import com.example.dontwastefood.Models.IngredientsIdResponse;
import com.example.dontwastefood.Models.IngredientsInfoResponse;
import com.example.dontwastefood.Models.MyPantry;
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

public class PantryManager {
    private static PantryManager single_instance = null;
    private List<MyPantry> pantry =new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private String ingredients;
    private RequestManager manager;
    private DatabaseHelper databaseHelper;
    private List<MyPantry> list=new ArrayList<>();
    private boolean complete = false;
    private String aisle;
    Context context;
    private PantryManager(){
        Log.i("PantryManager","in constructor" );
        this.firestore = FirebaseFirestore.getInstance();
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.auth = FirebaseAuth.getInstance();
        ingredients = "";
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
//        this.pantry = showPantry();


    }
    public static PantryManager getInstance(){
        if(single_instance == null){

            single_instance = new PantryManager();
        }
        return single_instance;
    }


    public void setAisle(String aisle) {

        this.aisle = aisle;
        Log.i("aisle listener",aisle);
    }

    public void addToPantry(String name, String image, String aisle, Context context){
        this.firestore = FirebaseFirestore.getInstance();
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.auth = FirebaseAuth.getInstance();
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        this.databaseHelper = new DatabaseHelper(context);
        File database = context.getDatabasePath(DatabaseHelper.dbName);
        // Toast.makeText(PantryActivity.this, "Added", Toast.LENGTH_SHORT).show();
        String saveCurrentDate;
        Calendar callForDate = Calendar.getInstance();
        int valability;
        valability = this.databaseHelper.getShelfLife(name);
this.context = context;
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        callForDate.add(Calendar.DAY_OF_MONTH,valability);
        saveCurrentDate = currentDate.format(callForDate.getTime());


        final HashMap<String, Object> pantryMap = new HashMap<>();
        String id = UUID.randomUUID().toString();
        pantryMap.put("id",id);
        pantryMap.put("name",name);
        pantryMap.put("image",image);
        pantryMap.put("date",saveCurrentDate);
//        Log.i("pantrymanager",aisle);
        pantryMap.put("aisle",aisle);

        firestore.collection("AddToPantry").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").document(id).set(pantryMap).
                addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
//       pantryAdapter.notifyDataSetChanged();

    }
    public List<MyPantry> getPantry() {
//pantry.clear();

//        showPantry();
        Log.i("in get",pantry.toString());
        while(complete == false){
            complete = false;
        }

        return pantry;
    }
    public  void showPantry(FirebaseCallback callback){
        this.pantry = new ArrayList<>();

        Log.i("in show","aici");
        firestore.collection("AddToPantry").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.i("PantryManager","on complete");
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){

                        MyPantry pantryModel = documentSnapshot.toObject(MyPantry.class);
                        pantry.add(pantryModel);

                        Log.i("PantryManager",pantry.get(0).getName());
                        ingredients = ingredients + pantryModel.getName()+",";


                    }
                    callback.onResponse(pantry,ingredients);
                    ingredients = "";
//                    setPantry(pantry);

                    //Toast.makeText(PantryActivity.this, ingredients, Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.w("PantryManager","Error");
                }
            }
        });
//        Log.i("PantryManager",on);
        Log.i("PantryManager",pantry.toString());

    }

    public void setPantry(List<MyPantry> pantry) {
        this.pantry = pantry;
        Log.i("setPantry",pantry.toString());
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

    public String getIngredients() {
        return ingredients;
    }

    public void updatePantry(String id, String date){


        firestore.collection("AddToPantry").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").document(id).update("date",date).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    public void deleteFromPantry(String id){
Log.i("delete","delete");
        firestore.collection("AddToPantry").document(auth.getCurrentUser().getUid())
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
        void onResponse(List<MyPantry> pantryList,String ingredientsList);
    }
    public interface AisleCallback{
        void onResponse(String aisle);
    }
}
