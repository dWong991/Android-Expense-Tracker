package com.example.expensetracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;


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
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    String saveDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //set up for date picker widget
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        Intent intent = getIntent();

        //set up for category picker widget
        autoCompleteTextView = findViewById(R.id.AutoCompleteTextview);
        mTextInput = findViewById(R.id.textInputLayout);
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
                    MainActivity3.changeItem(position, mEditText1.getText().toString(), mEditText3.getText().toString(), mEditText2.getText().toString(), text, dateButton.getText().toString());
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

                    MainActivity3.insertItem(mEditText1.getText().toString(), mEditText3.getText().toString(), mEditText2.getText().toString(), text, dateButton.getText().toString());
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
        String temp4 = MainActivity3.ExpenseList.get(position).getCategory();
        String temp5 = MainActivity3.ExpenseList.get(position).getDate();
        mTextView1 = findViewById(R.id.editName);
        mTextView1.setText(temp);
        mTextView2 = findViewById(R.id.editReason);
        mTextView2.setText(temp2);
        mTextView3 = findViewById(R.id.editCost);
        mTextView3.setText(temp3);
        autoCompleteTextView = findViewById(R.id.AutoCompleteTextview);
        autoCompleteTextView.setText(temp4);
        dateButton.setText(temp5);
    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener,year, month, day);
    }
    private String makeDateString(int day, int month, int year){
        return month + "/" + day + "/" + year;
    }
    private String getMonthFormat(int month){
        switch(month){
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEPT";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "JAN";
        }
    }
    public void openDatePicker(View v){
        datePickerDialog.show();
    }
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
}