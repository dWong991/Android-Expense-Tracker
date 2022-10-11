package com.example.expensetracker2;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class MyValueFormatter extends ValueFormatter {
    private final double total;

    public MyValueFormatter(double Total) {
        total = Total; // use one decimal
    }

    @Override
    public String getFormattedValue(float value) {
        return "$" + String.format("%.2f", (value * total)/100); // e.g. append percentage sign
    }
}
