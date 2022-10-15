package com.example.dontwastefood.Activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase database;
    public static final String dbName = "DontWasteFood.db";
    public static final int DATABASE_VERSION = 1;
    String dbPath;
    //String PATH = "C:\\Users\\rosem\\Downloads\\sqlitestudio-3.3.3\\SQLiteStudio\\";
    private Context context;
    public DatabaseHelper(@Nullable Context context) {
        super(context, dbName, null, DATABASE_VERSION);

        this.context = context;
        this.dbPath = "/data/data/" + context.getPackageName()+ "/"+"databases/";

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

//    public void checkDb(){
//        SQLiteDatabase checkDb = null;
//        String filePath = dbPath + dbName;
//        try{
//            checkDb = SQLiteDatabase.openDatabase(filePath,null,0);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        if(checkDb != null){
//            Toast.makeText(context, "Already exist", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            copyDatabase();
//        }
//    }

    protected boolean copyDatabase(Context context){
       // this.getReadableDatabase();

        try{
            InputStream ios = context.getAssets().open(dbName);
            OutputStream os = new FileOutputStream(dbPath+dbName);

            byte[] buffer = new byte[1024];
            int len ;
            while ((len = ios.read(buffer))> 0) {
                os.write(buffer,0,len);
            }
            os.flush();
            //ios.close();
            os.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    public void openDatabase(){
        String filePath = context.getDatabasePath(dbName).getPath();
        if(database != null && database.isOpen()){
            return;
        }

        database = SQLiteDatabase.openDatabase(filePath,null,SQLiteDatabase.OPEN_READWRITE);
    }
    public void closeDatabase(){
        if(database != null){
            database.close();
        }
    }
    public ArrayList<String> getAllData(){
        openDatabase();
        Cursor cursor = database.rawQuery("Select * from food",null);
        ArrayList<String> aList = new ArrayList<String>();
        while(cursor.moveToNext()){
            aList.add(cursor.getString(1));
        }
        cursor.close();
        closeDatabase();
        return aList;


    }
    public int getShelfLife(String food){
        openDatabase();
        int date=365;
        Cursor cursor = database.rawQuery("Select expiration_date from food"+ " Where lower(Name)= '" + food.toLowerCase(Locale.ROOT) +"';",null);

        while (cursor.moveToNext()){
            date = cursor.getInt(0);
        }

        cursor.close();
        closeDatabase();
//        Toast.makeText(context, "cuvant " + String.valueOf(date), Toast.LENGTH_SHORT).show();
        return date;
    }
}
