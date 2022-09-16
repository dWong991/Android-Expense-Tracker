package com.example.expensetracker2;

public class Expense {
    private int mImageResource;
    private String date;
    private String name;
    private String category;
    private double cost = 0;
    private String reason;
    private String note;

    public Expense(int imageResource, String newName, String newReason){
        mImageResource = imageResource;
        name = newName;
        reason = newReason;
    }

    public void changeText1(String text){
        name = text;
    }
    public int getImageResource(){
        return mImageResource;
    }
    public String getName(){
        return name;
    }
    public String getReason(){
        return reason;
    }
}
