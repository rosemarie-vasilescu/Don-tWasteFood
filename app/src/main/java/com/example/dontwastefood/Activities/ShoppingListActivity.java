package com.example.dontwastefood.Activities;

import static com.example.dontwastefood.Adapters.PantryAdapter.DELETE;
import static com.example.dontwastefood.Adapters.PantryAdapter.EDIT;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dontwastefood.Adapters.SearchShopItemAdapter;
import com.example.dontwastefood.Adapters.ShoppingListAdapter;
import com.example.dontwastefood.Listeners.IngredientClickListener;
import com.example.dontwastefood.Listeners.IngredientsResponseListener;
import com.example.dontwastefood.Listeners.MoreButtonClickListener;
import com.example.dontwastefood.Models.IngredientsResponse;
import com.example.dontwastefood.Models.ShoppingList;
import com.example.dontwastefood.R;
import com.example.dontwastefood.databinding.ActivityShoppingListBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {
    private static final int LABEL_VISIBILITY_LABELED = 1;
    private ActivityShoppingListBinding binding;
    private static final String TAG = "ShoppingListAcivity";
    SearchView searchView;
//    Button btn_search_recipe,submit;
    TextView no_ingredients;
    ProgressDialog dialog;
    RequestManager manager;
    RecyclerView recyclerView,recyclerView_shop;
    SearchShopItemAdapter searchShopItemAdapter;
    ShoppingListAdapter shoppingListAdapter;
    List<ShoppingList> list,shoppingList;
String shoppingString,uId,uQuantity;

    EditText edtTitle,edtMessage;
    TimePicker timePicker;
    DatePicker datePicker;
    String uname;
    DatePickerDialog datePickerDialog;
    BottomNavigationView navigationView;
    Dialog searchDialog,numberpickerDialog;
    ProgressDialog progressDialog;
    ShoppingListManager shoppingListManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setContentView(R.layout.activity_shopping_list);
        manager = new RequestManager(this);
shoppingString = "";
uId = "";
uQuantity = "";
        shoppingListManager = ShoppingListManager.getInstance();
//        pantryManager = PantryManager.getInstance();
//
//        list = pantryManager.list;
//        getList();
        searchDialog = new Dialog(this);

        list = new ArrayList<>();
        no_ingredients = findViewById(R.id.no_ingredients);
//       list = PantryManager.getInstance().getPantry();
//        Log.i("PantryManager",list.get(0).name);


//        Log.i("list",list.toString());
//      list = pantryManager.getPantry();
//        showPantry();
        uname = getIntent().getStringExtra("EDIT");
        navigationView = findViewById(R.id.bottom_nav);
        navigationView.setSelectedItemId(R.id.shoppingList);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

//        btn_search_recipe = findViewById(R.id.btn_search_recipe);

//        submit = findViewById(R.id.submit);
//        searchView = findViewById(R.id.searchView_pantry);

//        createNotificationChannel();

        recyclerView = searchDialog.findViewById(R.id.recycler_ingredients);
//        if(false == database.exists()){
//            databaseHelper.getReadableDatabase();
//            if(databaseHelper.copyDatabase(this)){
////                Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
//            }
//            else{
//                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }

        recyclerView_shop = findViewById(R.id.recycler_shopping_item);
        recyclerView_shop.setHasFixedSize(true);

        recyclerView_shop.setLayoutManager(new GridLayoutManager(ShoppingListActivity.this, 1));


//        Toast.makeText(this, list.get(0).name, Toast.LENGTH_SHORT).show();


//if(ingredients.equals("")){
//    no_ingredients.setVisibility(View.VISIBLE);
//}
//else
//{
//    no_ingredients.setVisibility(View.INVISIBLE);
//}

//        btn_search_recipe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(PantryActivity.this, RecipeByIngredientsActivity.class)
//                        .putExtra("ingredients",ingredients));
//            }
//        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading details...");
        progressDialog.show();
        getList();

        }
    private String getList(){
//        shoppingString = " ";
//TODO:de adaugat restul lucrurilor din pantryactivity
        shoppingListManager.showShoppingList(new ShoppingListManager.FirebaseCallback() {
            @Override
            public void onResponse(List<ShoppingList> shoppingList,String shopList) {
progressDialog.dismiss();
                list = shoppingList;
                shoppingListAdapter = new ShoppingListAdapter(ShoppingListActivity.this,list,moreButtonClickListener);
                 shoppingString= shopList ;
                recyclerView_shop.setAdapter(shoppingListAdapter);
                shoppingListAdapter.notifyDataSetChanged();
                Log.i("list1",list.toString());
                Log.i("list2",shoppingString);

            }
        });
        return shoppingString;
    }
    public void showNumberPicker(String id,String quantity){
        Dialog dialog = new Dialog(ShoppingListActivity.this);
        dialog.setTitle("NumberPicker");
        dialog.setContentView(R.layout.dialog_picknumber);
        int q = Integer.parseInt(quantity);
        Button btn_cancel, btn_save;
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_save = dialog.findViewById(R.id.btn_save);
        NumberPicker np = dialog.findViewById(R.id.numberPicker_quantity);
        np.setMinValue(1);
        np.setMaxValue(100);
        np.setValue(q);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShoppingListActivity.this, String.valueOf(np.getValue()), Toast.LENGTH_SHORT).show();
                shoppingListManager.updateShoppingList(id,String.valueOf(np.getValue()));
                getList();
                dialog.dismiss();
                //sa pun in adapter cantitatea
            }
        });
        dialog.show();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.searchView);


        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.searchView){
            showDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private final IngredientsResponseListener ingredientsResponseListener = new IngredientsResponseListener() {
        @Override
        public void didFetch(List<IngredientsResponse> response, String message) {

//            recyclerView = findViewById(R.id.recycler_ingredients);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(ShoppingListActivity.this, 1));
            searchShopItemAdapter = new SearchShopItemAdapter(ShoppingListActivity.this, response,ingredientClickListener);
            recyclerView.setAdapter(searchShopItemAdapter);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(ShoppingListActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    private void showDialog() {
        EditText searchView ;
        Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.search_bottom_sheet);
        recyclerView = dialog1.findViewById(R.id.recycler_ingredients);

        searchView = dialog1.findViewById(R.id.edt_search_text);

        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {

                    return true;
                }
                return false;
            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                manager.getIngredients(ingredientsResponseListener, String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ImageView ic_camera,ic_back ;
        ic_camera=dialog1.findViewById(R.id.iv_camera);
        ic_back=dialog1.findViewById(R.id.ic_back);
//        ic_camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(PantryActivity.this, "click", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(PantryActivity.this, SearchObjectDetectionActivity.class).putExtra("ingredients",ingredients));
//            }
//        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        dialog1.show();

        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog1.getWindow().setGravity(Gravity.BOTTOM);
    }
    private final IngredientClickListener ingredientClickListener = new IngredientClickListener() {
        @Override
        public void onIngredientClicked(String name, String id, String image,String aisle,int positon) {
            //Toast.makeText(PantryActivity.this, "Added", Toast.LENGTH_SHORT).show();

            if(list.toString().contains(name)){
                Log.i("list to string",list.toString());
                Toast.makeText(ShoppingListActivity.this, "Already in your shopping list, if you want to add more, you can update later", Toast.LENGTH_SHORT).show();
//                shoppingListManager.updateShoppingList(id,String.valueOf(2));
                recyclerView_shop.setVisibility(View.VISIBLE);

            }
            else{
                shoppingListManager.addToShoppingList(name, "1",ShoppingListActivity.this);
getList();
            }



        }
    };
    public String getId(String name){
        shoppingListManager.getId(name, new ShoppingListManager.IdCallback() {
            @Override
            public void onResponse(String id) {
uId = id;
            }
        });
        return uId;
    }
    private final MoreButtonClickListener moreButtonClickListener = new MoreButtonClickListener() {
        @Override
        public void onMoreButtonClicked(String option,String id, String name,String quantity,View view) {
//           Toast.makeText(PantryActivity.this, option, Toast.LENGTH_SHORT).show();


            switch (option){
                case EDIT:
                   Toast.makeText(ShoppingListActivity.this, name, Toast.LENGTH_SHORT).show();
                    //dialog cu numere
                    showNumberPicker(id,quantity);
                    break;

                case DELETE:
                    shoppingListManager.deleteFromShoppingList(id);
                    getList();
//                   pantryAdapter.notifyDataSetChanged();
//                   deleteFromPantry(id);
                    break;
            }

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.home:
//                    Toast.makeText(PantryActivity.this, "click", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ShoppingListActivity.this,MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.pantry:
                    startActivity(new Intent(ShoppingListActivity.this,PantryActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.chatbot:
                    startActivity(new Intent(ShoppingListActivity.this,ChatBotActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.shoppingList:
                   return true;
            }
            return false;
        }
    };
}