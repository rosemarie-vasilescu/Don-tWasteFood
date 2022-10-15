package com.example.dontwastefood.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dontwastefood.Adapters.PantryAdapter;
import com.example.dontwastefood.Adapters.RandomRecipeAdapter;
import com.example.dontwastefood.Adapters.RecipeByIngredientsAdapter;
import com.example.dontwastefood.Listeners.RecipeByIngredientsListener;
import com.example.dontwastefood.Listeners.RecipeClickListener;
import com.example.dontwastefood.Models.MyPantry;
import com.example.dontwastefood.Models.RecipeByIngredientsResponse;
import com.example.dontwastefood.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeByIngredientsActivity extends AppCompatActivity {
    Dialog dialog;
    RequestManager manager;
    RecipeByIngredientsAdapter recipeByIngredientsAdapter;
    RecyclerView recyclerView;
//    Button search,btn_to_pantry;
Button search,btn_apply,btn_filter,btn_reset,txt_reset;
    Chip btn_gluten_free,btn_ketogenic,btn_vegetarian,btn_pescetarian,btn_vegan,btn_main_course,btn_dessert,btn_salad,btn_drink,btn_breakfast,btn_snack,btn_appetizer,btn_side,btn_french,btn_indian,btn_thai,btn_chinese,btn_mexican,btn_medit,btn_dairy,btn_peanut,btn_soy,btn_egg,btn_gluten;
    ChipGroup container_intolerances,container_meal_types,container_cuisines,container_diets ;
    Spinner spinner;
    SearchView searchView;
    String ingredients;
    List<String> tags = new ArrayList<>();
    ActionBar actionBar;
    String tagsList;
    LinearLayout txt_no_recipe;
    TextView no_recipe;
    PantryManager pantryManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_by_ingredients);
        pantryManager=PantryManager.getInstance();
        tagsList="";
        ingredients = getIntent().getStringExtra("ingredients");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);
no_recipe=findViewById(R.id.no_recipe);
        manager = new RequestManager(this);
        if(ingredients == null){
        pantryManager.showPantry(new PantryManager.FirebaseCallback() {
            @Override
            public void onResponse(List<MyPantry> pantryList, String ingredientsList) {
                if(ingredientsList.equals("")){
                    no_recipe.setVisibility(View.VISIBLE);
                }
                else{
                    manager.getRecipeByIngredients(recipeByIngredientsListener,ingredientsList);}
            }
        });}
        else
        {
            manager.getRecipeByIngredients(recipeByIngredientsListener,ingredients);
        }



//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });




    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(RecipeByIngredientsActivity.this,PantryActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private final RecipeByIngredientsListener recipeByIngredientsListener = new RecipeByIngredientsListener() {
        @Override
        public void didFetch(List<RecipeByIngredientsResponse> response, String message) {

            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(RecipeByIngredientsActivity.this, 1));

            recipeByIngredientsAdapter = new RecipeByIngredientsAdapter(RecipeByIngredientsActivity.this, response, recipeClickListener);
            recyclerView.setAdapter(recipeByIngredientsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeByIngredientsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(RecipeByIngredientsActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id",id));
        }
    };




}