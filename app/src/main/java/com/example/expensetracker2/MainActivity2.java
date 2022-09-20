package com.example.expensetracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;



public class MainActivity2 extends AppCompatActivity {
    public TextView mTextView1;
    public TextView mTextView2;
    public TextView mTextView3;
    public EditText mEditText1;
    public EditText mEditText2;
    public EditText mEditText3;
    public TextInputLayout mTextInput;
    public AutoCompleteTextView autoCompleteTextView;
    public static ArrayList<String> Categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();


        autoCompleteTextView = findViewById(R.id.AutoCompleteTextview);
        mTextInput = findViewById(R.id.textInputLayout);
//        Categories.add("Food");
//        Categories.add("Utilities");
//        Categories.add("Transportation");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, Categories);
        autoCompleteTextView.setAdapter(adapter);

        if(intent.hasExtra("position") ){
            populateFields();
            mEditText1 = findViewById(R.id.editName);
            mEditText2 = findViewById(R.id.editReason);
            mEditText3 = findViewById(R.id.editCost);
            Button submitButton = findViewById(R.id.buttonSubmit);
            submitButton.setText("Update");
            submitButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String text = String.valueOf(autoCompleteTextView.getText());
                    if(!Categories.contains(text)){
                        adapter.add(text);
                        Categories.add(text);
                    }
                    int position = intent.getIntExtra("position", 0);
                    MainActivity3.changeItem(position, mEditText1.getText().toString(), mEditText3.getText().toString(), mEditText2.getText().toString());
                    saveData();
                    finish();
                }
            });
        }
        else{
            mEditText1 = findViewById(R.id.editName);
            mEditText2 = findViewById(R.id.editReason);
            mEditText3 = findViewById(R.id.editCost);
            Button submitButton = findViewById(R.id.buttonSubmit);
            submitButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String text = String.valueOf(autoCompleteTextView.getText());
                    if(!Categories.contains(text)){
                        adapter.add(text);
                        Categories.add(text);
                    }
                    MainActivity3.insertItem(mEditText1.getText().toString(), mEditText3.getText().toString(), mEditText2.getText().toString());
                    saveData();
                    finish();
                }
            });
        }
    }
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared Preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(MainActivity3.ExpenseList);
        editor.putString("Expense List", json);

        Gson gson2 = new Gson();
        String json2 = gson2.toJson(Categories);
        editor.putString("Categories", json2);

        editor.apply();
    }
    public void populateFields(){
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        String temp = MainActivity3.ExpenseList.get(position).getName();
        String temp2 = MainActivity3.ExpenseList.get(position).getReason();
        String temp3 = MainActivity3.ExpenseList.get(position).getCost();
        mTextView1 = findViewById(R.id.editName);
        mTextView1.setText(temp);
        mTextView2 = findViewById(R.id.editReason);
        mTextView2.setText(temp2);
        mTextView3 = findViewById(R.id.editCost);
        mTextView3.setText(temp3);
    }
}