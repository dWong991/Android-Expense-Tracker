package com.example.expensetracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity4 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public static Spinner spinner2;
    public static Spinner spinner;
    public TextView dayView;
    public ArrayAdapter<CharSequence> adapter;
    public ArrayAdapter<String> adapter2;
    public static PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        pieChart = findViewById(R.id.activity_main4_piechart);
        setupPieChart();
        loadPieChartData();
        CustomMarkerView mv = new CustomMarkerView(this, R.layout.custom_marker);
        pieChart.setMarker(mv);

        dayView = findViewById(R.id.textViewDay);
        Button dayButton = findViewById(R.id.buttonDay);
        Button monthButton = findViewById(R.id.buttonMonth);
        Button applyButton = findViewById(R.id.buttonSelect);

        spinner = findViewById(R.id.spinnerMonth);
        adapter = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner2 = findViewById(R.id.spinnerYear);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, populateYear());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);



        applyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
    }
    protected void onResume(){
        super.onResume();

        double total = GetTotal(MainActivity3.ExpenseList);
        pieChart.setCenterText("Total \n" + "$" + String.format(Locale.US, "%.2f", total));
        loadPieChartData();

        //update spinners everytime we return to mainactivity4 based on added values
        spinner2 = findViewById(R.id.spinnerYear);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, populateYear());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
    }
    public ArrayList<String> populateYear(){
        ArrayList<String> yearList = new ArrayList<>();
        yearList.add("ALL");
        for(int i = 0; i < MainActivity3.ExpenseList.size(); i++){
            if(!yearList.contains(String.valueOf(MainActivity3.ExpenseList.get(i).getYear()))){
                yearList.add(String.valueOf(MainActivity3.ExpenseList.get(i).getYear()));
            }
        }
        return yearList;
    }

    public int getYearIndex(Spinner spinner, int year){
        if(spinner.getCount() == 0) {
            return -1;
        }
        else {

            for (int i = 0; i < spinner.getCount(); i++) {
                if (spinner.getItemAtPosition(i).toString().equals(String.valueOf(year))) {
                    return i;
                }
            }
            return -1;
        }
    }

    public int getMonthIndex(Spinner spinner, int month){
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEPT", "OCT", "NOV", "DEC"};
        String monthString = months[month];
        if(spinner.getCount() == 0) {
            return -1;
        }
        else {
            for (int i = 0; i < spinner.getCount(); i++) {
                if (spinner.getItemAtPosition(i).toString().equals(monthString)){
                    return i;
                }
            }
            return -1;
        }
    }

    public void dayPicker(View view){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                spinner.setSelection(getMonthIndex(spinner, month), true);
                spinner2.setSelection(getYearIndex(spinner2, year), true);
                adapter.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
                dayView.setText(String.valueOf(day));
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, style, dateSetListener,year, month, day);
        datePickerDialog.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        //update grand total display for the chart whenever the app is loaded
        double total = GetTotal(MainActivity3.ExpenseList);
        pieChart.setCenterText("Total \n" + "$" + String.format(Locale.US, "%.2f", total));
        pieChart.setCenterTextSize(24);
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setEnabled(false);
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
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setValueTextColor(Color.BLACK);
//        dataSet.setSliceSpace(2);
//        dataSet.setValueLinePart1OffsetPercentage(90);
//        dataSet.setValueLinePart1Length(0.5f);
//        dataSet.setValueLinePart2Length(0.5f);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        double total = GetTotal(MainActivity3.ExpenseList);
        data.setValueFormatter(new MyValueFormatter(total));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);



        pieChart.setExtraBottomOffset(20f);
        pieChart.setData(data);

        pieChart.setTransparentCircleRadius(45);
        pieChart.setHoleRadius(40);

        //pieChart.setExtraBottomOffset(40f);
        //pieChart.setExtraTopOffset(40f);
//        pieChart.setExtraLeftOffset(40f);
//        pieChart.setExtraRightOffset(40f);
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

    public double GetTotal(ArrayList<Expense> filteredExpenseList){
        double expense_total = 0.00;
        for(int i = 0; i < filteredExpenseList.size(); i++){
            expense_total += filteredExpenseList.get(i).getCostAmount();
        }
        return expense_total;
    }
    public void resetFilters(View view){
        spinner.setSelection(0);
        spinner2.setSelection(0);
        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        dayView.setText("ALL");
    }
}