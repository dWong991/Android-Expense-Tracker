package com.example.expensetracker2;

import com.github.mikephil.charting.formatter.ValueFormatter;
import java.util.Locale;

public class MyValueFormatter extends ValueFormatter {
    private final double total;

    public MyValueFormatter(double Total) {
        total = Total;
    }

    @Override
    public String getFormattedValue(float value) {
        //total * percentage to get category total
        return "$" + String.format(Locale.US, "%.2f", (value * total)/100);
    }
}
