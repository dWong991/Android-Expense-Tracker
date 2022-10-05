package com.example.expensetracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
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

public class MainActivity4 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    BarChart barChart;
    public static Spinner spinner2;
    public static Spinner spinner;
    public ArrayAdapter<CharSequence> adapter;
    public ArrayAdapter<Integer> adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        barChart = findViewById(R.id.barChart_view);
        //setupBarChart();
        //loadBarChartData();
        Button dayButton = findViewById(R.id.buttonDay);
        Button monthButton = findViewById(R.id.buttonMonth);
        Button yearButton = findViewById(R.id.buttonYear);

        spinner = findViewById(R.id.spinnerMonth);
        adapter = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner2 = findViewById(R.id.spinnerYear);
        adapter2 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, populateYear());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
    }
    protected void onResume(){
        super.onResume();
        //rebuild data each time we return back to main activity?
        //maybe check for a way to see if the data is modified then call loadPieChartData()
        //but for now it works as intended, will optimize later
        //update grand total display for the chart whenever the main activity1 is resumed
        spinner2 = findViewById(R.id.spinnerYear);
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, populateYear());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
    }
    public ArrayList<Integer> populateYear(){
        ArrayList<Integer> yearList = new ArrayList<>();
        for(int i = 0; i < MainActivity3.ExpenseList.size(); i++){
            if(!yearList.contains(MainActivity3.ExpenseList.get(i).getYear())){
                yearList.add(MainActivity3.ExpenseList.get(i).getYear());
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
                if (spinner.getItemAtPosition(i) == year) {
                    return i;
                }
            }
            return -1;
        }
    }

    public int getMonthIndex(Spinner spinner, int month){
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        String monthString = months[month];
        if(monthString == null || spinner.getCount() == 0) {
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
    private String makeDateString(int day, int month, int year){
        return month + "/" + day + "/" + year;
    }

    public void dayPicker(View view){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                spinner.setSelection(getMonthIndex(spinner, month), true);
                spinner2.setSelection(getYearIndex(spinner2, "2020"), true);
                adapter.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
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

    private void setupBarChart(){
        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadBarChartData(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 200));
        entries.add(new BarEntry(2, 400));
        entries.add(new BarEntry(3, 50));
        entries.add(new BarEntry(4, 900));
        entries.add(new BarEntry(5, 550));

//        ArrayList<BarEntry> entries2 = new ArrayList<>();
//        entries2.add(new BarEntry(1, 100));
//        entries2.add(new BarEntry(2, 500));
//        entries2.add(new BarEntry(3, 660));
//        entries2.add(new BarEntry(4, 200));
//        entries2.add(new BarEntry(5, 50));
//
        BarDataSet dataSet = new BarDataSet(entries, "DataSet1");
//        barDataSet1.setColor(Color.RED);
//
//        BarDataSet barDataSet2 = new BarDataSet(entries2, "DataSet2");
//        barDataSet1.setColor(Color.BLACK);
//
//        BarData data = new BarData(barDataSet1,barDataSet2);
//        barChart.setData(data);
        String[] days = new String[]{"UTILITIES", "TRANSPORTATION", "FOOD"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1);
//        xAxis.setGranularityEnabled(true);
//
//        barChart.setDragEnabled(true);
//        barChart.setVisibleXRangeMaximum(3);
//
//        float barSpace = 0.08f;
//        float groupSpace = 0.44f;
//        data.setBarWidth(0.10f);
//
//        barChart.getXAxis().setAxisMinimum(0);
//        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * 7);
//        barChart.getAxisLeft().setAxisMinimum(0);
//        barChart.groupBars(0, groupSpace, barSpace);
//
//        barChart.invalidate();


        //BarDataSet dataSet = new BarDataSet(entries, "Expense Categories");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(16f);
        BarData data = new BarData(dataSet);
        //data.setDrawValues(true);
        data.setValueTextColor(Color.BLACK);
        barChart.setFitBars(true);
        barChart.setData(data);
        barChart.getDescription().setText("Expenses");
        barChart.animateY(1400, Easing.EaseInOutQuad);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();

        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}