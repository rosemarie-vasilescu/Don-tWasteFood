package com.example.dontwastefood.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dontwastefood.Adapters.SearchIngredientsAdapter;
import com.example.dontwastefood.Listeners.IngredientClickListener;
import com.example.dontwastefood.Listeners.IngredientsResponseListener;
import com.example.dontwastefood.Models.Ingredient;
import com.example.dontwastefood.Models.IngredientsResponse;
import com.example.dontwastefood.Models.MyPantry;
import com.example.dontwastefood.R;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.common.Barcode;

import org.pytorch.IValue;
import org.pytorch.LiteModuleLoader;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SearchObjectDetectionActivity extends AppCompatActivity implements Runnable {
    private int mImageIndex = 0;
    private String[] mTestImages = {"aicook2.jpg", "aicook3.jpg"};
private List<IngredientsResponse> ingredients;
    private ImageView mImageView;
    private ResultView mResultView;
    private Button mButtonDetect;
    private ProgressBar mProgressBar;
    private Bitmap mBitmap = null;
    private Module mModule = null;
    HashSet<String> resultsSet = new HashSet<>();
    ArrayList<Result> results;
    Dialog dialog1;
    RecyclerView recyclerView;
    PantryManager pantryManager;
    ImageView ic_down ;

//    Dialog dialog1 = new Dialog(this);

    private float mImgScaleX, mImgScaleY, mIvScaleX, mIvScaleY, mStartX, mStartY;
    private  RequestManager manager;
    private SearchIngredientsAdapter searchIngredientsAdapter;
    public static String assetFilePath(Context context, String assetName) throws IOException {
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(assetName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

        setContentView(R.layout.activity_search_object_detecion);

        try {
            mBitmap = BitmapFactory.decodeStream(getAssets().open(mTestImages[mImageIndex]));
        } catch (IOException e) {
            Log.e("Object Detection", "Error reading assets", e);
            finish();
        }
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                                Barcode.FORMAT_QR_CODE,
                                Barcode.FORMAT_AZTEC)
                        .build();
        manager = new RequestManager(getApplicationContext());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ingredients = new ArrayList<>();
        mImageView = findViewById(R.id.imageView);
        mImageView.setImageBitmap(mBitmap);
        mResultView = findViewById(R.id.resultView);
        mResultView.setVisibility(View.INVISIBLE);
        dialog1 = new Dialog(this);


        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.bottom_object_search);
        ic_down= dialog1.findViewById(R.id.ic_down);
        ic_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        pantryManager = PantryManager.getInstance();

//        recyclerView = dialog1.findViewById(R.id.recycler_ingredients);

//        dialog1.setContentView(R.layout.bottom_object_search);
        recyclerView = dialog1.findViewById(R.id.recycler_ingredients);
//        final Button buttonTest = findViewById(R.id.testButton);
//        buttonTest.setText(("Test Image 1/3"));
//        buttonTest.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mResultView.setVisibility(View.INVISIBLE);
//                mImageIndex = (mImageIndex + 1) % mTestImages.length;
//                buttonTest.setText(String.format("Text Image %d/%d", mImageIndex + 1, mTestImages.length));
//
//                try {
//                    mBitmap = BitmapFactory.decodeStream(getAssets().open(mTestImages[mImageIndex]));
//                    mImageView.setImageBitmap(mBitmap);
//                } catch (IOException e) {
//                    Log.e("Object Detection", "Error reading assets", e);
//                    finish();
//                }
//            }
//        });


        final Button buttonSelect = findViewById(R.id.selectButton);
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mResultView.setVisibility(View.INVISIBLE);

                final CharSequence[] options = { "Choose from Photos", "Take Picture", "Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchObjectDetectionActivity.this);
                builder.setTitle("New Image");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Picture")) {
                            Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, 0);
                        }
                        else if (options[item].equals("Choose from Photos")) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto , 1);
                        }
                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

//        final Button buttonLive = findViewById(R.id.liveButton);
//        buttonLive.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                final Intent intent = new Intent(SearchObjectDetectionActivity.this, ObjectDetectionActivity.class);
//                startActivity(intent);
//            }
//        });

        mButtonDetect = findViewById(R.id.detectButton);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mButtonDetect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mButtonDetect.setEnabled(false);
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
                mButtonDetect.setText(getString(R.string.run_model));

                mImgScaleX = (float)mBitmap.getWidth() / PrePostProcessor.mInputWidth;
                mImgScaleY = (float)mBitmap.getHeight() / PrePostProcessor.mInputHeight;

                mIvScaleX = (mBitmap.getWidth() > mBitmap.getHeight() ? (float)mImageView.getWidth() / mBitmap.getWidth() : (float)mImageView.getHeight() / mBitmap.getHeight());
                mIvScaleY  = (mBitmap.getHeight() > mBitmap.getWidth() ? (float)mImageView.getHeight() / mBitmap.getHeight() : (float)mImageView.getWidth() / mBitmap.getWidth());

                mStartX = (mImageView.getWidth() - mIvScaleX * mBitmap.getWidth())/2;
                mStartY = (mImageView.getHeight() -  mIvScaleY * mBitmap.getHeight())/2;

                Thread thread = new Thread(SearchObjectDetectionActivity.this);
                thread.start();
//                Log.i("results",results.toString());
//searchIngredient(results);
            }
        });

        try {
            mModule = LiteModuleLoader.load(SearchObjectDetectionActivity.assetFilePath(getApplicationContext(), "best.torchscript.ptl"));
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("aicook.txt")));
            String line;
            List<String> classes = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                classes.add(line);
            }
            PrePostProcessor.mClasses = new String[classes.size()];
            classes.toArray(PrePostProcessor.mClasses);
        } catch (IOException e) {
            Log.e("Object Detection", "Error reading assets", e);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(SearchObjectDetectionActivity.this,PantryActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        mBitmap = (Bitmap) data.getExtras().get("data");
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90.0f);
                        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
                        mImageView.setImageBitmap(mBitmap);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                mBitmap = BitmapFactory.decodeFile(picturePath);
                                Matrix matrix = new Matrix();
                                matrix.postRotate(90.0f);
                                mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
                                mImageView.setImageBitmap(mBitmap);
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void run() {

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(mBitmap, PrePostProcessor.mInputWidth, PrePostProcessor.mInputHeight, true);
        final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(resizedBitmap, PrePostProcessor.NO_MEAN_RGB, PrePostProcessor.NO_STD_RGB);
        IValue[] outputTuple = mModule.forward(IValue.from(inputTensor)).toTuple();
        final Tensor outputTensor = outputTuple[0].toTensor();
        final float[] outputs = outputTensor.getDataAsFloatArray();
        results =  PrePostProcessor.outputsToNMSPredictions(outputs, mImgScaleX, mImgScaleY, mIvScaleX, mIvScaleY, mStartX, mStartY);

//        resultsSet.addAll(results);
        for (Result ele : results) {
            resultsSet.add(PrePostProcessor.mClasses[ele.classIndex]);
             }
        for (String ele : resultsSet) {
            Log.i("search ingredients",ele);
            manager.getIngredients(ingredientsResponseListener,ele);
        }

        Log.i("SearchObject", String.valueOf(results.get(1).classIndex + " " + results.get(1).score + " " + results.get(1).rect));
        runOnUiThread(() -> {
            mButtonDetect.setEnabled(true);
            mButtonDetect.setText(getString(R.string.detect));
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            mResultView.setResults(results);
            mResultView.invalidate();
            mResultView.setVisibility(View.VISIBLE);
            showDialog();

        });

    }

    private final IngredientsResponseListener ingredientsResponseListener = new IngredientsResponseListener() {
        @Override
        public void didFetch(List<IngredientsResponse> response, String message) {

//            recyclerView = findViewById(R.id.recycler_ingredients);
            if (response != null){
            ingredients.addAll(response);}
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(SearchObjectDetectionActivity.this, 1));
            searchIngredientsAdapter = new SearchIngredientsAdapter(SearchObjectDetectionActivity.this, ingredients,ingredientClickListener);
            recyclerView.setAdapter(searchIngredientsAdapter);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(SearchObjectDetectionActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final IngredientClickListener ingredientClickListener = new IngredientClickListener() {
        @Override
        public void onIngredientClicked(String name, String id, String image,String aisle,int position) {
            pantryManager.showPantry(new PantryManager.FirebaseCallback() {
                @Override
                public void onResponse(List<MyPantry> pantryList, String ingredientsList) {
                    if( ingredientsList.contains(name) ){
               Toast.makeText(SearchObjectDetectionActivity.this, "Already in your pantry", Toast.LENGTH_SHORT).show();

           }
            else {

           pantryManager.addToPantry(name,image,aisle,SearchObjectDetectionActivity.this);
           searchIngredientsAdapter.notifyDataSetChanged();
            Toast.makeText(SearchObjectDetectionActivity.this, "Added", Toast.LENGTH_SHORT).show();

           }
                }
            });


//            Toast.makeText(SearchObjectDetectionActivity.this, "Added", Toast.LENGTH_SHORT).show();
//            Toast.makeText(SearchObjectDetectionActivity.this, pantryManager.getIngredients(), Toast.LENGTH_SHORT).show();
//            if( pantryManager.getIngredients().contains(name) ||  getIntent().getStringExtra("ingredients").contains(name)){
//                Toast.makeText(SearchObjectDetectionActivity.this, "Already in your pantry", Toast.LENGTH_SHORT).show();
//
//            }
//            else {
//
//            pantryManager.addToPantry(name,image,SearchObjectDetectionActivity.this);
////            searchIngredientsAdapter.notifyDataSetChanged();
//            Toast.makeText(SearchObjectDetectionActivity.this, "Added", Toast.LENGTH_SHORT).show();
//
//            }
        }
    };
    private void showDialog() {
        EditText searchView ;

//        final Window window = dialog1.getWindow();
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////
////        window.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//
//        dialog1.show();


//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                manager.getIngredients(ingredientsResponseListener,newText);
//return true;
//            }
//        });

        dialog1.show();

        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog1.getWindow().setGravity(Gravity.BOTTOM);
    }
}
