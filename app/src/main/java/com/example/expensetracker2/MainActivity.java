package com.example.expensetracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}