package com.example.expensetracker2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;

//this class will handle the modify/delete expense using a recycler view and card view
public class MainActivity3 extends AppCompatActivity {
    public static ArrayList<Expense> ExpenseList  = new ArrayList<>();
    private RecyclerView mRecyclerView;
    public static ExpenseAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        buildRecyclerView();
    }

    public static void insertItem(String name, String costs, String reason, String category, String date){
        ExpenseList.add(new Expense(R.drawable.ic_android, name, costs, reason, category, date));
        //mAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position){
        ExpenseList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
    public static void changeItem(int position, String name, String costs, String reason, String category, String date){
        ExpenseList.get(position).modifyExpense(name, costs, reason, category,date);
        mAdapter.notifyItemChanged(position);
    }
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared Preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity3.ExpenseList);
        editor.putString("Expense List", json);
        editor.apply();
    }
    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManger = new LinearLayoutManager(this);
        mAdapter = new ExpenseAdapter(ExpenseList);

        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                //handle clicks here
                //changeItem(position, "clicked");
                //open activity2
                Intent intent = new Intent(v.getContext(), MainActivity2.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, 1);
            }
            @Override
            public void onDeleteClick(int position) {
                //handle clicks here
                removeItem(position);
                saveData();
            }
        });
    }
}