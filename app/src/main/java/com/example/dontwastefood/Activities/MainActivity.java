package com.example.dontwastefood.Activities;

import static java.lang.Character.toLowerCase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dontwastefood.Adapters.RandomRecipeAdapter;

import com.example.dontwastefood.Listeners.RandomRecipeResponseListener;
import com.example.dontwastefood.Listeners.RecipeClickListener;
import com.example.dontwastefood.Models.MyPantry;
import com.example.dontwastefood.Models.RandomRecipeApiResponse;
import com.example.dontwastefood.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {
   ProgressDialog dialog1;
    Dialog dialog;
    ProgressDialog progressDialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;
    Button search,btn_apply,btn_filter,btn_reset,txt_reset;
    Chip btn_gluten_free,btn_ketogenic,btn_vegetarian,btn_pescetarian,btn_vegan,btn_main_course,btn_dessert,btn_salad,btn_drink,btn_breakfast,btn_snack,btn_appetizer,btn_side,btn_french,btn_indian,btn_thai,btn_chinese,btn_mexican,btn_medit,btn_dairy,btn_peanut,btn_soy,btn_egg,btn_gluten;
    ChipGroup container_intolerances,container_meal_types,container_cuisines,container_diets ;
    Spinner spinner;
    SearchView searchView;
    List<String> tags = new ArrayList<>();
    ActionBar actionBar;
    String tagsList;
    BottomNavigationView navigationView;
    LinearLayout txt_no_recipe;
    PantryManager pantryManager;
    List<MyPantry> pantry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//pantryManager = PantryManager.getInstance();
//pantry = pantryManager.showPantry();
//pantry = pantryManager.getPantry();
        tagsList="";
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);
        txt_no_recipe=findViewById(R.id.txt_no_recipe);
        chips_init(dialog);
        txt_reset = findViewById(R.id.txt_reset);

        navigationView = findViewById(R.id.bottom_nav);
        navigationView.setSelectedItemId(R.id.home);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
        btn_filter = findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
//        dialog1 = new ProgressDialog(this);
//       dialog1.setTitle("Loading...");
        searchView = findViewById(R.id.searchView_home);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                manager.getRandomRecipes(randomRecipeResponseListener,query);
               // dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        manager = new RequestManager(this);
        manager.getRandomRecipes(randomRecipeResponseListener,tagsList);
//        dialog.show();
        txt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckChips();
                manager.getRandomRecipes(randomRecipeResponseListener,tagsList);
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading details...");
        progressDialog.show();

    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            progressDialog.dismiss();
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));

            if(response.getRecipes().isEmpty()){
                Toast.makeText(MainActivity.this, "No recipe found", Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.GONE);
                txt_no_recipe.setVisibility(View.VISIBLE);
            }
else {
                randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this, response.getRecipes(), recipeClickListener);
                recyclerView.setAdapter(randomRecipeAdapter);
                recyclerView.setVisibility(View.VISIBLE);
                txt_no_recipe.setVisibility(View.GONE);
            }

        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };


    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(MainActivity.this, RecipeDetailsActivity.class)
            .putExtra("id",id));
        }
    };
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {


                case R.id.home:
                    return true;
                case R.id.pantry:
                    startActivity(new Intent(MainActivity.this,PantryActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.chatbot:
                    startActivity(new Intent(MainActivity.this,ChatBotActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.shoppingList:
                    startActivity(new Intent(MainActivity.this,ShoppingListActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        }
    };
    private void showDialog(){


        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> ids=container_cuisines.getCheckedChipIds();
                for (Integer id:ids){
                    Chip chip = container_cuisines.findViewById(id);
                    tagsList = tagsList + (String)chip.getText() +",";
                    //....
                }

                ids = container_diets.getCheckedChipIds();
                for (Integer id:ids){
                    Chip chip = container_diets.findViewById(id);
                    tagsList = tagsList + (String)chip.getText() +",";
                    //....
                }
                ids = container_meal_types.getCheckedChipIds();
                for (Integer id:ids){
                    Chip chip = container_meal_types.findViewById(id);
                    tagsList = tagsList + (String)chip.getText() +",";
                    //....
                }
                ids = container_intolerances.getCheckedChipIds();
                for (Integer id:ids){
                    Chip chip = container_intolerances.findViewById(id);
                    tagsList = tagsList + (String)chip.getText() +",";
                    //....
                }
                if(tagsList.equals("")){
                    manager.getRandomRecipes(randomRecipeResponseListener, tagsList );
                    dialog.dismiss();
                }
                else {
                    manager.getRandomRecipes(randomRecipeResponseListener, tagsList.substring(0, tagsList.length() - 1).toLowerCase(Locale.ROOT));
//                    Toast.makeText(MainActivity.this, tagsList.substring(0, tagsList.length() - 1), Toast.LENGTH_SHORT).show();
                    tagsList = "";
                    dialog.dismiss();
                }
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               uncheckChips();

            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


private void chips_init(Dialog dialog){
    btn_gluten_free = dialog.findViewById(R.id.btn_gluten_free);
    btn_ketogenic = dialog.findViewById(R.id.btn_ketogenic);
    btn_vegetarian = dialog.findViewById(R.id.btn_vegetarian);
    btn_pescetarian = dialog.findViewById(R.id.btn_pescetarian);
    btn_vegan = dialog.findViewById(R.id.btn_vegan);
    btn_main_course = dialog.findViewById(R.id.btn_main);
    btn_dessert = dialog.findViewById(R.id.btn_dessert);
    btn_salad = dialog.findViewById(R.id.btn_salad);
    btn_drink = dialog.findViewById(R.id.btn_drink);
    btn_breakfast = dialog.findViewById(R.id.btn_breakfast);
    btn_snack = dialog.findViewById(R.id.btn_snack);
    btn_appetizer = dialog.findViewById(R.id.btn_appetizer);
    btn_side = dialog.findViewById(R.id.btn_side_dish);
    btn_french = dialog.findViewById(R.id.btn_french);
    btn_indian = dialog.findViewById(R.id.btn_indian);
    btn_thai = dialog.findViewById(R.id.btn_thai);
    btn_chinese = dialog.findViewById(R.id.btn_chinese);
    btn_mexican = dialog.findViewById(R.id.btn_mexican);
    btn_medit = dialog.findViewById(R.id.btn_mediterranean);
    btn_dairy = dialog.findViewById(R.id.btn_dairy);
    btn_peanut = dialog.findViewById(R.id.btn_peanut);
    btn_soy = dialog.findViewById(R.id.btn_soy);
    btn_egg = dialog.findViewById(R.id.btn_Egg);
    btn_gluten = dialog.findViewById(R.id.btn_gluten);
    btn_apply = dialog.findViewById(R.id.btn_apply);
    btn_reset = dialog.findViewById(R.id.btn_reset);

     container_intolerances = dialog.findViewById(R.id.container_intolerances);
    container_meal_types = dialog.findViewById(R.id.container_meal_types);
    container_cuisines = dialog.findViewById(R.id.container_cuisines);
     container_diets = dialog.findViewById(R.id.container_diets);
}

 private void uncheckChips(){
     container_cuisines.clearCheck();
     container_diets.clearCheck();
     container_meal_types.clearCheck();
     container_intolerances.clearCheck();
 }

}