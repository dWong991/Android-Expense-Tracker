package com.example.expensetracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the list of expenses and categories saved
        loadData();

        Button modifyButton = findViewById(R.id.buttonModify);
        Button addButton = findViewById(R.id.buttonAdd);
        Button settingsButton = findViewById(R.id.buttonSettings);
        Button dataButton = findViewById(R.id.buttonData);

        //set up pie chart
        pieChart = findViewById(R.id.activity_main_piechart);
        setupPieChart();
        loadPieChartData();


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
        dataButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity4.class);
                startActivityForResult(intent, 1);
            }
        });
    }
    protected void onResume(){
        super.onResume();
        //rebuild data each time we return back to main activity?
        //maybe check for a way to see if the data is modified then call loadPieChartData()
        //but for now it works as intended, will optimize later
        loadPieChartData();
    }


    //function to load sharedpreferences data
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared Preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Expense List", null);
        Type type = new TypeToken<ArrayList<Expense>>() {}.getType();
        MainActivity3.ExpenseList = gson.fromJson(json, type);

        Gson gson2 = new Gson();
        String json2 = sharedPreferences.getString("Categories", null);
        Type type2 = new TypeToken<ArrayList<String>>() {}.getType();
        MainActivity2.Categories = gson2.fromJson(json2, type2);
    }

    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Expense by Category");
        pieChart.setCenterTextSize(24);

        pieChart.setCenterTextColor(Color.BLACK);

        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData(){
        ArrayList<PieEntry> entries = createPieChartData();
        ArrayList<Integer> colors = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }
        for(int color: ColorTemplate.VORDIPLOM_COLORS){
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        //data has changed, and need to refresh
        pieChart.invalidate();
        //animation in milliseconds
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
    public ArrayList<PieEntry> createPieChartData(){
        //filtertype = Day, Month, or Year
        //date = day, or the month, or the year inputted?
        //need to be able to filter by day, month, or year
        ArrayList<PieEntry> ExpenseChartData = new ArrayList<>();
        //format data to be easily added to the PieEntry array list
        //use hashmap to have string = category and double = percentage
        HashMap<String, Double> expenseDataMap = new HashMap<>();

        double total = 0.0;
//        for(int i = 0; i < MainActivity3.ExpenseList.size(); i++){
//            expenseDataMap.merge(MainActivity3.ExpenseList.get(i).getCategory(), MainActivity3.ExpenseList.get(i).getCostAmount(), Double::sum);
//            total += MainActivity3.ExpenseList.get(i).getCostAmount();
//        }
        for(int i = 0; i < MainActivity3.ExpenseList.size(); i++){
            if(expenseDataMap.containsKey(MainActivity3.ExpenseList.get(i).getCategory())){
                expenseDataMap.put(MainActivity3.ExpenseList.get(i).getCategory(), expenseDataMap.get(MainActivity3.ExpenseList.get(i).getCategory()) + MainActivity3.ExpenseList.get(i).getCostAmount());
            }
            else{
                expenseDataMap.put(MainActivity3.ExpenseList.get(i).getCategory(), MainActivity3.ExpenseList.get(i).getCostAmount());
            }
            total += MainActivity3.ExpenseList.get(i).getCostAmount();
        }
        for(String i : expenseDataMap.keySet()){
            ExpenseChartData.add(new PieEntry((float)(expenseDataMap.get(i)/total), i));
        }
        return ExpenseChartData;
    }

}