/*
    This activity will handle the input and modification of expenses to the list
 */
package com.example.expensetracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity2 extends AppCompatActivity {

    public TextView mTextViewName;
    public TextView mTextViewReason;
    public TextView mTextViewCost;
    public TextView mTextViewNote;

    public EditText mEditName;
    public EditText mEditReason;
    public EditText mEditCost;
    public EditText mEditNote;

    public TextInputLayout mTextInput;
    public AutoCompleteTextView autoCompleteTextView;
    public static ArrayList<String> Categories = new ArrayList<>();
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //set up for date picker widget
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        Intent intent = getIntent();

        //set up for category picker widget (autotext complete to fill guess what category you are entering,
        //or use drop down menu option
        autoCompleteTextView = findViewById(R.id.AutoCompleteTextview);
        mTextInput = findViewById(R.id.textInputLayout);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, Categories);
        autoCompleteTextView.setAdapter(adapter);

        //set up for the following field functions
        mEditName = findViewById(R.id.editName);
        mEditReason = findViewById(R.id.editReason);
        mEditCost = findViewById(R.id.editCost);
        mEditNote = findViewById(R.id.editNotes);
        Button submitButton = findViewById(R.id.buttonSubmit);

        if(intent.hasExtra("position") ){
            populateFields();
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
                    MainActivity3.changeItem(position, mEditName.getText().toString(), mEditCost.getText().toString(), mEditReason.getText().toString(), text, dateButton.getText().toString(), mEditNote.getText().toString());
                    saveData();
                    finish();
                }
            });
        }
        else{
            submitButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String text = String.valueOf(autoCompleteTextView.getText());
                    if(!Categories.contains(text)){
                        adapter.add(text);
                        Categories.add(text);
                    }
                    MainActivity3.insertItem(mEditName.getText().toString(), mEditCost.getText().toString(), mEditReason.getText().toString(), text, dateButton.getText().toString(), mEditNote.getText().toString());
                    saveData();
                    finish();
                }
            });
        }
    }
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared Preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //save expense list
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity3.ExpenseList);
        editor.putString("Expense List", json);
        //save categories
        //need to figure out a way to delete improperly entered values, or user HAS to be sure they enter the
        //correct category, as this is currently permanent
        Gson gson2 = new Gson();
        String json2 = gson2.toJson(Categories);
        editor.putString("Categories", json2);

        editor.apply();
    }
    public void populateFields(){
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        //get field values for expense item, might combine to 1 line in teh setText() function later
        String expenseName = MainActivity3.ExpenseList.get(position).getName();
        String expenseReason = MainActivity3.ExpenseList.get(position).getReason();
        String expenseCost = MainActivity3.ExpenseList.get(position).getCost();
        String expenseCategory = MainActivity3.ExpenseList.get(position).getCategory();
        String expenseDate = MainActivity3.ExpenseList.get(position).getDate();
        String expenseNote = MainActivity3.ExpenseList.get(position).getNote();

        mTextViewName = findViewById(R.id.editName);
        mTextViewName.setText(expenseName);
        mTextViewReason = findViewById(R.id.editReason);
        mTextViewReason.setText(expenseReason);
        mTextViewCost = findViewById(R.id.editCost);
        mTextViewCost.setText(expenseCost);
        mTextViewNote = findViewById(R.id.editNotes);
        mTextViewNote.setText(expenseNote);
        autoCompleteTextView = findViewById(R.id.AutoCompleteTextview);
        autoCompleteTextView.setText(expenseCategory);
        dateButton.setText(expenseDate);

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