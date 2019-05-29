package com.example.mycontactapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contact2019.db";
    public static final String TABLE_NAME = "Contact2019_table";
    public static final String ID = "ID";
    public static final String COLUMN_NAME_CONTACT = "contact";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "+ TABLE_NAME + " (" +
                        ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME_CONTACT + " TEXT)";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase db = this.getWritableDatabase();  //FOR TEST ONLY - WILL REMOVE LATER
        Log.d("MyContactApp", "DatabaseHelper: constructed DatabaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MyContactApp", "DatabaseHelper: creating DatabaseHelper");
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MyContactApp", "DatabaseHelper: upgrading DatabaseHelper");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    public boolean insertData(String name){
        Log.d("MyContactApp", "DatabaseHelper: inserting data");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CONTACT, name);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            Log.d("MyContactApp", "DatabaseHelper: contact insert FAILED");
            return false;
        }
        else{
            Log.d("MyContactApp", "DatabaseHelper: contact insert PASSED");
            return true;
        }
    }




}
