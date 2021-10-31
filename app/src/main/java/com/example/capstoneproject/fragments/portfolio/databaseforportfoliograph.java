package com.example.capstoneproject.fragments.portfolio;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Date;

public class databaseforportfoliograph extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "databaseforportfoliograph.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "id";
    private static final String STOCK_NAME = "stock_name";
    private static final String AMOUNT = "total_amount";
    private static final String INDUSTRY = "industry";
    private static final String DATE_ENTRY = "date_entry";


    public databaseforportfoliograph(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STOCK_NAME + " TEXT, " + AMOUNT + " TEXT, " + INDUSTRY + " TEXT, " + DATE_ENTRY + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    Cursor readAllData() {
        String query = "Select * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateData(String row_id, String stock_name, String total_amount, String industry,String date_entry){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STOCK_NAME, stock_name);
        cv.put(AMOUNT, total_amount);
        cv.put(INDUSTRY, industry);
        cv.put(DATE_ENTRY, date_entry);

        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void addentry( String stock_name, String total_amount, String industry,String date_entry){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STOCK_NAME, stock_name);
        cv.put(AMOUNT, total_amount);
        cv.put(INDUSTRY, industry);
        cv.put(DATE_ENTRY, date_entry);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
