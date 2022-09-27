package com.example.expensetracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

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

public class MainActivity4 extends AppCompatActivity {
    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        barChart = findViewById(R.id.barChart_view);
        //setupBarChart();
        loadBarChartData();

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

}