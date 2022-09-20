package com.example.expensetracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        Button modifyButton = findViewById(R.id.buttonModify);
        Button addButton = findViewById(R.id.buttonAdd);
        //set up button click listener, must be in onCreate() function
        modifyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity3.class);
                startActivityForResult(intent, 1);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), MainActivity2.class);
                startActivityForResult(intent, 1);
            }
        });
    }


    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared Preferences",MODE_PRIVATE);
        Gson gson = new Gson();
//        Gson gson2 = new Gson();
        String json = sharedPreferences.getString("Expense List", null);
        Type type = new TypeToken<ArrayList<Expense>>() {}.getType();
//
//        String json2 = sharedPreferences.getString("Categories", null);
//        Type type2 = new TypeToken<ArrayList<String>>() {}.getType();

        MainActivity3.ExpenseList = gson.fromJson(json, type);
//        MainActivity2.Categories = gson2.fromJson(json2, type2);
    }
}