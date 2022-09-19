package com.example.expensetracker2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

//this class will handle the modify/delete expense using a recycler view and card view
public class MainActivity3 extends AppCompatActivity {
    public static ArrayList<Expense> ExpenseList  = new ArrayList<>();
    private RecyclerView mRecyclerView;
    public static ExpenseAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;
    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //createExpenseList();
        buildRecyclerView();
        //setButtons();

    }

    public static void insertItem(String name, String costs, String reason){
        ExpenseList.add(new Expense(R.drawable.ic_android, name, costs, reason));
        //mAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position){
        ExpenseList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
    public static void changeItem(int position, String name, String costs, String reason){
        ExpenseList.get(position).modifyExpense(name, costs, reason);
        mAdapter.notifyItemChanged(position);
    }
    public void createExpenseList(){
        ExpenseList = new ArrayList<>();
//        ExpenseList.add(new Expense(R.drawable.ic_android, "Dim Sum", "Lunch"));
//        ExpenseList.add(new Expense(R.drawable.ic_android, "Eggs and Bacon", "Breakfast"));
//        ExpenseList.add(new Expense(R.drawable.ic_android, "Steak", "Dinner"));
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
            }
        });
    }
//    public void setButtons(){
//        buttonInsert = findViewById(R.id.button_insert);
//        buttonRemove = findViewById(R.id.button_remove);
//        editTextInsert = findViewById(R.id.edittext_insert);
//        editTextRemove = findViewById(R.id.edittext_remove);
//
//        buttonInsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = Integer.parseInt(editTextInsert.getText().toString());
//                insertItem(position);
//            }
//        });
//
//        buttonRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = Integer.parseInt(editTextRemove.getText().toString());
//                removeItem(position);
//            }
//        });
//    }
}