package com.example.mycontactapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editPhone, editAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editPhone = findViewById(R.id.editText_phone2);
        editAddress = findViewById(R.id.editText_address);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated DatabaseHelper");
    }

    public void addData(View view) {
        Cursor cursor = myDb.getAllData();
        while (cursor.getCount() > 0 && cursor.moveToNext()) {
            if (editName.getText().toString().equals(cursor.getString(1))
                    && editPhone.getText().toString().equals(cursor.getString(2))
                    && editAddress.getText().toString().equals(cursor.getString(3))) {
                Toast.makeText(MainActivity.this, "Nuh uh! Contact already in database HEHE", Toast.LENGTH_LONG).show();
                return;
            }
        }

        boolean isInserted = myDb.insertData(editName.getText().toString(), editPhone.getText().toString(),
                editAddress.getText().toString());

        if (isInserted) {
            Toast.makeText(MainActivity.this, "Success - contact inserted GO YOU", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(MainActivity.this, "FAILED - contact not inserted SUCKS TO SUCK", Toast.LENGTH_LONG).show();
        }
    }
    /*public void addData(View view) {
        if (searchData(view))
        boolean isInserted = myDb.insertData(editName.getText().toString(),editPhone.getText().toString(), editAddress.getText().toString() );
        if (isInserted == true) {
            Toast.makeText(MainActivity.this, "Success - contact inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Failed - contact not inserted", Toast.LENGTH_LONG).show();

        }
    }*/

    public void viewData(View view){
        Cursor res = myDb.getAllData();

        if(res.getCount() == 0){
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            //append res column 0, ... to the buffer - see Stringbuffer and Cursor apis
            buffer.append("ID: " + res.getString(0 ) + "\n");
            buffer.append("Name: " + res.getString(1) + "\n");
            buffer.append("Number: " + res.getString(3) + "\n");
            buffer.append("Address: " + res.getString(2) + "\n");
            buffer.append("\n");
        }

        showMessage("Data", buffer.toString());
    }

    public void showMessage(String title, String message){
        Log.d("MyContactApp", "DatabaseHelper: show message");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void searchData(View view) {
        Log.d("MyContactApp", "MainActivity: search data");
        Cursor res = myDb.getAllData();

        if (res.getCount()==0) {
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            //append res column 0, ... to the buffer = see Stringbuffer and Cursor api's
            boolean match = true;
            if (editName.getText().toString().length()!=0 && !editName.getText().toString().equals(res.getString(1))) match=false;
            if (editAddress.getText().toString().length()!=0 && !editAddress.getText().toString().equals(res.getString(2))) match=false;
            if (editPhone.getText().toString().length()!=0 && !editPhone.getText().toString().equals(res.getString(3))) match=false;

            if (match){
                buffer.append("ID: " + res.getString(0) + "\n");
                buffer.append("Name: " + res.getString(1) + "\n");
                buffer.append("Number: " + res.getString(3) + "\n");
                buffer.append("Address: " + res.getString(2) + "\n");
                buffer.append("\n");
            }
        }

        showMessage("Data", buffer.toString());
    }
}
