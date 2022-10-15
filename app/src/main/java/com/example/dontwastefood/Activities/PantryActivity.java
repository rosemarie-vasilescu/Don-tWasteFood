package com.example.dontwastefood.Activities;

import static com.example.dontwastefood.Activities.Notification.channelId;
import static com.example.dontwastefood.Activities.Notification.messageExtra;
import static com.example.dontwastefood.Activities.Notification.notificationId;
import static com.example.dontwastefood.Activities.Notification.titleExtra;
import static com.example.dontwastefood.Adapters.PantryAdapter.DELETE;
import static com.example.dontwastefood.Adapters.PantryAdapter.EDIT;
import static com.example.dontwastefood.Adapters.PantryAdapter.SHOPPING;
import static java.lang.Integer.parseInt;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.dontwastefood.Adapters.CategoryAdapter;
import com.example.dontwastefood.Adapters.PantryAdapter;
import com.example.dontwastefood.Adapters.SearchIngredientsAdapter;
import com.example.dontwastefood.Listeners.IngredientClickListener;
import com.example.dontwastefood.Listeners.IngredientsIdResponseListener;
import com.example.dontwastefood.Listeners.IngredientsInfoListener;
import com.example.dontwastefood.Listeners.IngredientsResponseListener;
import com.example.dontwastefood.Listeners.IngredientsUPCListener;
import com.example.dontwastefood.Listeners.MoreButtonClickListener;
import com.example.dontwastefood.Models.Category;
import com.example.dontwastefood.Models.IngredientUPCResponse;
import com.example.dontwastefood.Models.IngredientsIdResponse;
import com.example.dontwastefood.Models.IngredientsInfoResponse;
import com.example.dontwastefood.Models.IngredientsResponse;
import com.example.dontwastefood.Models.MyPantry;
import com.example.dontwastefood.Models.ShoppingList;
import com.example.dontwastefood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class PantryActivity extends AppCompatActivity {
    private static final String TAG = "PantryAcivity";
  SearchView searchView;
//    Button btn_search_recipe,submit;
TextView no_ingredients;
    ProgressDialog dialog;
    RequestManager manager;
    ShoppingListManager shoppingListManager;
    RecyclerView recyclerView,recyclerView_pantry;
    SearchIngredientsAdapter searchIngredientsAdapter;
    PantryAdapter pantryAdapter;
    CategoryAdapter categoryAdapter;
    List<MyPantry> list;
    List<Category> categoryList;
    String ingredients;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    DatabaseHelper databaseHelper;
    EditText edtTitle,edtMessage;
    TimePicker timePicker;
    DatePicker datePicker;
    String uname;
    DatePickerDialog datePickerDialog;
    BottomNavigationView navigationView;
    Dialog dialog1;
    PantryManager pantryManager;
ExtendedFloatingActionButton btn_search_recipe;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        manager = new RequestManager(this);
        firestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseHelper = new DatabaseHelper(this);
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.dbName);

       pantryManager = PantryManager.getInstance();
       shoppingListManager = ShoppingListManager.getInstance();
//        pantryManager = PantryManager.getInstance();
//
//        list = pantryManager.list;
//        getList();
        dialog1 = new Dialog(this);
        ingredients = "";
        list = new ArrayList<>();
        categoryList = new ArrayList<>();
        no_ingredients = findViewById(R.id.no_ingredients);
//       list = PantryManager.getInstance().getPantry();
//        Log.i("PantryManager",list.get(0).name);


//        Log.i("list",list.toString());
//      list = pantryManager.getPantry();
//        showPantry();
        uname = getIntent().getStringExtra("EDIT");
        navigationView = findViewById(R.id.bottom_nav);
        navigationView.setSelectedItemId(R.id.pantry);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        btn_search_recipe = findViewById(R.id.btn_search_recipe);

//        submit = findViewById(R.id.submit);
//        searchView = findViewById(R.id.searchView_pantry);

        createNotificationChannel();

        recyclerView = dialog1.findViewById(R.id.recycler_ingredients);
        if(false == database.exists()){
            databaseHelper.getReadableDatabase();
            if(databaseHelper.copyDatabase(this)){
//                Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        recyclerView_pantry = findViewById(R.id.recycler_ingredients_avaible);
        recyclerView_pantry.setHasFixedSize(true);

        recyclerView_pantry.setLayoutManager(new LinearLayoutManager(PantryActivity.this));


//        Toast.makeText(this, list.get(0).name, Toast.LENGTH_SHORT).show();


//if(ingredients.equals("")){
//    no_ingredients.setVisibility(View.VISIBLE);
//}
//else
//{
//    no_ingredients.setVisibility(View.INVISIBLE);
//}

        btn_search_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PantryActivity.this, RecipeByIngredientsActivity.class)
                        );
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading details...");
        progressDialog.show();
getList();

    }
    private void initDatePicker(String id,String name,String date){

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            Long time = null;

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {


              //  time = makeDateString(day,month,year);
                Calendar calendar = Calendar.getInstance();


                calendar.set(year,month,day,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));


                scheduleNotification(calendar.getTimeInMillis(),id,name);





            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        //
        String input =date;
        DateTimeFormatter formatter = null;
        int y = 1;
        int m = 1;
        int dayOfMonth = 1;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern( "MM dd, yyyy" );
            LocalDate localDate = LocalDate.parse( input , formatter );
            y = localDate.getYear();
            m = localDate.getMonthValue();
            dayOfMonth = localDate.getDayOfMonth();
        }

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener,year,month,day);
        datePickerDialog.updateDate(y,m-1,dayOfMonth);


    }
    private void groupList(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Log.i("response4",list.toString());
            categoryList.clear();
            Map<String, List<MyPantry>> grouped = list.stream().collect(Collectors.groupingBy(p -> p.getAisle()));
            for(Map.Entry<String,List<MyPantry>> p : grouped.entrySet()){
                Log.i("category",p.getKey() + " " + p.getValue().toString());
                categoryList.add(new Category(p.getValue(),p.getKey()));
            }
            Log.i("response5",categoryList.toString());


        }

    }
    private void getList(){

        pantryManager.showPantry(new PantryManager.FirebaseCallback() {
            @Override
            public void onResponse(List<MyPantry> pantryList,String ingredientsList) {
                progressDialog.dismiss();
                Log.i("response",pantryList.toString());
                list = pantryList;





//                pantryAdapter = new PantryAdapter(PantryActivity.this,list,moreButtonClickListener);
                categoryAdapter = new CategoryAdapter(PantryActivity.this,categoryList,moreButtonClickListener);
                groupList();
                Log.i("response2",pantryList.toString());
                Log.i("response3",categoryList.toString());
//                recyclerView_pantry.setAdapter(pantryAdapter);
                recyclerView_pantry.setAdapter(categoryAdapter);
//                pantryAdapter.notifyDataSetChanged();
                categoryAdapter.notifyDataSetChanged();
                Log.i("list1",list.toString());



            }
        });
    }

    private void openDatePicker(View view){
        datePickerDialog.show();
    }

    private final IngredientsResponseListener ingredientsResponseListener = new IngredientsResponseListener() {
        @Override
        public void didFetch(List<IngredientsResponse> response, String message) {

//            recyclerView = findViewById(R.id.recycler_ingredients);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(PantryActivity.this, 1));
            searchIngredientsAdapter = new SearchIngredientsAdapter(PantryActivity.this, response,ingredientClickListener);
            recyclerView.setAdapter(searchIngredientsAdapter);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(PantryActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.searchView);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.searchView){
            showDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
        ImageView ic_camera,ic_back,ic_barcode ;
        ic_camera=dialog1.findViewById(R.id.iv_camera);
        ic_barcode=dialog1.findViewById(R.id.iv_barcode);
        ic_back=dialog1.findViewById(R.id.ic_back);
        ic_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(PantryActivity.this, "click", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PantryActivity.this, SearchObjectDetectionActivity.class).putExtra("ingredients",ingredients));
            }
        });
        ic_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });
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
    public void findId(String name){
        manager = new RequestManager(PantryActivity.this);
        manager.getIngredientsId(ingredientsIdResponseListener, name);
    }
    private final IngredientsIdResponseListener ingredientsIdResponseListener = new IngredientsIdResponseListener() {
        @Override
        public void didFetch(IngredientsIdResponse response, String message) {
            Log.i("id",String.valueOf(response.getResults().get(0).getId()));
            findAisle(response.getResults().get(0).getId());
        }

        @Override
        public void didError(String message) {

        }
    };
    public void findAisle(int id){
        manager = new RequestManager(PantryActivity.this);
        manager.getIngredientsInfo(ingredientsInfoListener, id);
    }
    private final IngredientsInfoListener ingredientsInfoListener = new IngredientsInfoListener() {
        @Override
        public void didFetch(IngredientsInfoResponse response, String message) {
            Log.i("aisle listener",response.getAisle());
            pantryManager.addToPantry(response.getName(),response.getImage(),response.getAisle(),PantryActivity.this);

            getList();


        }

        @Override
        public void didError(String message) {

        }
    };
    private final IngredientClickListener ingredientClickListener = new IngredientClickListener() {
        @Override
        public void onIngredientClicked(String name, String id, String image,String aisle,int position) {
            //Toast.makeText(PantryActivity.this, "Added", Toast.LENGTH_SHORT).show();
            if(ingredients.contains(name)){
                Toast.makeText(PantryActivity.this, "Already in your pantry", Toast.LENGTH_SHORT).show();
                recyclerView_pantry.setVisibility(View.VISIBLE);
            }
            else {
//                pantryManager.addToPantry(name,image,aisle,PantryActivity.this);
                findId(name);
                Log.i("pantryactivity",aisle);

//                addToPantry(name, image);
//showPantry();

//pantryAdapter.notifyDataSetChanged();
            }
        }
    };
   private final MoreButtonClickListener moreButtonClickListener = new MoreButtonClickListener() {
       @Override
       public void onMoreButtonClicked(String option,String id, String name,String date,View view) {
//           Toast.makeText(PantryActivity.this, option, Toast.LENGTH_SHORT).show();


           switch (option){
               case EDIT:
//                   Toast.makeText(PantryActivity.this, name, Toast.LENGTH_SHORT).show();
                   initDatePicker(id,name,date);
                   openDatePicker(view);
                   break;
               case SHOPPING:
                   addToShoppingList(name);

                   break;
               case DELETE:
                   pantryManager.deleteFromPantry(id);
                   getList();
//                   pantryAdapter.notifyDataSetChanged();
//                   deleteFromPantry(id);
                   break;
           }

       }
   };



    private void createNotificationChannel(){
        String name= " Notif channel";
        String desc = "Description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,name,importance);
            channel.setDescription(desc);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
notificationManager.cancelAll();
        }


    }
    private String scheduleNotification(Long time,String id,String name){
        Intent intent = new Intent(getApplicationContext(),Notification.class);

        String title = "Low Expiration Date";
        String message = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase()+ " is due to expire";
        intent.putExtra(titleExtra,name);
        intent.putExtra(messageExtra,message);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                (int) System.currentTimeMillis(),
                intent,PendingIntent.FLAG_IMMUTABLE
        );
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    pendingIntent
            );
        }
        showAlert(time,id,title,message);

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
//        Toast.makeText(this, currentDate.format(time), Toast.LENGTH_SHORT).show();
        return currentDate.format(time);
    }
    private void showAlert(Long time, String id,String title, String message){
        Date date = new Date(time);
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        Date timedate = new Date();
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
//        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
       AlertDialog builder =  new AlertDialog.Builder(this)
                .setTitle("Notification")
                .setMessage( message +" on "+ currentDate.format(date) )
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        pantryManager.updatePantry(id,currentDate.format(date));
                        getList();
//                        updatePantry(id,currentDate.format(date));
                    }
                })
               .setCancelable(true)
                .show();

    }
  @RequiresApi(api = Build.VERSION_CODES.M)
  private Long getTime()
    {

            int minute = timePicker.getMinute();
            int hour = timePicker.getHour();
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();




        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day,hour,minute);
        return calendar.getTimeInMillis();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.home:
//                    Toast.makeText(PantryActivity.this, "click", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PantryActivity.this,MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.pantry:

                    return true;
                case R.id.chatbot:
                  startActivity(new Intent(PantryActivity.this,ChatBotActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.shoppingList:
                    startActivity(new Intent(PantryActivity.this,ShoppingListActivity.class));
                    overridePendingTransition(0,0);
                    return true;

            }
            return false;
        }
    };
    private void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    private IngredientsUPCListener ingredientsUPCListener = new IngredientsUPCListener() {
        @Override
        public void didFetch(IngredientUPCResponse response, String message) {
            if(response == null) {
                Toast.makeText(PantryActivity.this, "Oops, we're sorry. Looks like we didn't find any ingredients that contain this barcode.", Toast.LENGTH_SHORT).show();

            }

               try {

                   manager.getIngredients(ingredientsResponseListener, response.getBreadcrumbs().get(0));
               } catch(Exception e) {
                   Toast.makeText(PantryActivity.this, "Oops, we're sorry. Looks like we didn't find any ingredients that contain this barcode.", Toast.LENGTH_SHORT).show();
               }

        }


        @Override
        public void didError(String message) {

        }
    };
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(),result -> {
         if(result.getContents()!= null){
//             AlertDialog.Builder builder = new AlertDialog.Builder(PantryActivity.this);
//             builder.setTitle("Result");
//             builder.setMessage(result.getContents());
             String upc = result.getContents();
             if(upc.length() > 12){

                 manager.getIngredientsUPC(ingredientsUPCListener,upc.substring(1));

             }
             else{
                 manager.getIngredientsUPC(ingredientsUPCListener,upc);
             }

             manager.getIngredientsUPC(ingredientsUPCListener,upc);
//             builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                 @Override
//                 public void onClick(DialogInterface dialogInterface, int i) {
//                     dialogInterface.dismiss();
//                 }
//             }).show();
         }
    });
    private void addToShoppingList(String name)
    {
        shoppingListManager.showShoppingList(new ShoppingListManager.FirebaseCallback() {
        @Override
        public void onResponse(List<ShoppingList> shoppingList, String shopList) {
            if(shopList.contains(name)){
                Toast.makeText(PantryActivity.this, "Already in your shopping list, if you want to add more, you can update later", Toast.LENGTH_SHORT).show();

            }
            else{
                shoppingListManager.addToShoppingList(name,"1",PantryActivity.this);
            }
        }
    });


    }
}

