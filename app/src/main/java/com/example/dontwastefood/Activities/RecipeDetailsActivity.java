package com.example.dontwastefood.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.text.Html;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dontwastefood.Adapters.IngredientsAdapter;
import com.example.dontwastefood.Adapters.InstructionsAdapter;
import com.example.dontwastefood.Adapters.ShoppingListAdapter;
import com.example.dontwastefood.Adapters.SimilarRecipeAdapter;
import com.example.dontwastefood.Listeners.IngredientClickListener;
import com.example.dontwastefood.Listeners.InstructionsListener;
import com.example.dontwastefood.Listeners.RecipeClickListener;
import com.example.dontwastefood.Listeners.RecipeDetailsListener;
import com.example.dontwastefood.Listeners.SimilarRecipeListener;
import com.example.dontwastefood.Models.InstructionsResponse;
import com.example.dontwastefood.Models.RecipeDetailsResponse;
import com.example.dontwastefood.Models.ShoppingList;
import com.example.dontwastefood.Models.SimilarRecipeResponse;
import com.example.dontwastefood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    int id;
    TextView textView_meal_name, textView_meal_source,textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredients,recycler_meal_similar,recycler_meal_instructions;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionsAdapter instructionAdapter;
    ShoppingListManager shoppingListManager;
    String shoppingString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
shoppingListManager = ShoppingListManager.getInstance();
shoppingString = "";
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       manager = new RequestManager(this);
//
       manager.getRecipeDetails(recipeDetailsListener, id);
       manager.getSimilarRecipe(similarRecipeListener,id);
       manager.getInstructions(instructionsListener,id);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading details...");
        dialog.show();
        findViews();
    }
    private void findViews(){
        textView_meal_name = findViewById(R.id.textView_meal_name);

        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);
        recycler_meal_similar= findViewById(R.id.recycler_meal_similar);
        recycler_meal_instructions= findViewById(R.id.recycler_meal_instructions);


    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {

            dialog.dismiss();

            textView_meal_name.setText(response.getTitle());

            textView_meal_summary.setText(Html.fromHtml(response.getSummary()).toString());
            Picasso.get().load(response.getImage()).into(imageView_meal_image);

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,LinearLayoutManager.VERTICAL,false));

            ingredientsAdapter = new IngredientsAdapter(RecipeDetailsActivity.this,response.getExtendedIngredients(),ingredientClickListener);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    private String getList(){
//        shoppingString = " ";
//TODO:de adaugat restul lucrurilor din pantryactivity
        shoppingListManager.showShoppingList(new ShoppingListManager.FirebaseCallback() {
            @Override
            public void onResponse(List<ShoppingList> shoppingList, String shopList) {

                shoppingString= shopList ;

            }
        });
        return shoppingString;
    }
    private final IngredientClickListener ingredientClickListener = new IngredientClickListener() {
        @Override
        public void onIngredientClicked(String name, String id, String image,String aisle,int positon) {
            //Toast.makeText(PantryActivity.this, "Added", Toast.LENGTH_SHORT).show();

            if(getList().contains(name)){
                Toast.makeText(RecipeDetailsActivity.this, "Already in your shopping list", Toast.LENGTH_SHORT).show();
//                shoppingListManager.updateShoppingList(id,String.valueOf(2));


            }
            else{
                shoppingListManager.addToShoppingList(name, "1",RecipeDetailsActivity.this);
                getList();
            }}};
    private final SimilarRecipeListener similarRecipeListener = new SimilarRecipeListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            recycler_meal_similar.setHasFixedSize(true);
            recycler_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,LinearLayoutManager.HORIZONTAL,false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetailsActivity.this,response, recipeClickListener);
            recycler_meal_similar.setAdapter(similarRecipeAdapter);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
           startActivity(new Intent(RecipeDetailsActivity.this,RecipeDetailsActivity.class)
           .putExtra("id",id));
        }
    };

    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void didFetch(List<InstructionsResponse> response, String message) {
            recycler_meal_instructions.setHasFixedSize(true);
            recycler_meal_instructions.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,LinearLayoutManager.VERTICAL,false));
            instructionAdapter = new InstructionsAdapter(RecipeDetailsActivity.this,response);
            recycler_meal_instructions.setAdapter(instructionAdapter);
        }

        @Override
        public void didError(String message) {

        }
    };

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(RecipeDetailsActivity.this,MainActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}