package com.example.expensetracker2;

public class Expense {
    private int mImageResource;
    private String date;
    private String name;
    private String category;
    private String cost;
    private double costAmount = 0;
    private String reason;
    private String note;

    public Expense(int imageResource, String newName, String newCosts, String newReason){
        mImageResource = imageResource;
        name = newName;
        cost = newCosts;
        costAmount = Double.parseDouble(newCosts);
        reason = newReason;
    }
    public void modifyExpense(String modName, String modCost, String modReason){
        name = modName;
        cost = modCost;
        reason = modReason;
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
    public String getCost() { return cost; }
}
