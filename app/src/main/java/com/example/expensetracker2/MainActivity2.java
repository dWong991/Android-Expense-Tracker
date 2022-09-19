package com.example.expensetracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;



public class MainActivity2 extends AppCompatActivity {
    public TextView mTextView1;
    public TextView mTextView2;

    public EditText mEditText1;
    public EditText mEditText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //populateFields();
        mEditText1 = findViewById(R.id.editName);
        mEditText2 = findViewById(R.id.editReason);
        Button submitButton = findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity3.insertItem(mEditText1.getText().toString(), mEditText2.getText().toString());
                saveData();
                finish();
            }
        });
    }
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared Preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity3.ExpenseList);
        editor.putString("Expense List", json);
        editor.apply();
    }
//    public void populateFields(){
//        Intent intent = getIntent();
//        int position = intent.getIntExtra("position", 0);
//        String temp = MainActivity3.ExpenseList.get(position).getName();
//        String temp2 = MainActivity3.ExpenseList.get(position).getReason();
//
//        mTextView1 = findViewById(R.id.editName);
//        mTextView1.setText(temp);
//        mTextView2 = findViewById(R.id.editReason);
//        mTextView2.setText(temp2);
//    }
}