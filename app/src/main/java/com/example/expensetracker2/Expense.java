package com.example.expensetracker2;

public class Expense {
    private int mImageResource;
    private String date = "";
    private String name = "";
    private String category = "";
    private String cost = "";
    private String reason = "";
    private String note = "";

    private double costAmount = 0;
    private int day, month, year;

    public Expense(int imageResource, String newName, String newCosts, String newReason, String newCategory, String newDate){
        mImageResource = imageResource;
        name = newName;
        cost = newCosts;
        costAmount = Double.parseDouble(cost);
        reason = newReason;
        category = newCategory;
        date = newDate;
        extractDate(date);
    }
    public void modifyExpense(String modName, String modCost, String modReason, String modCategory, String modDate){
        name = modName;
        cost = modCost;
        costAmount = Double.parseDouble(cost);
        reason = modReason;
        category = modCategory;
        date = modDate;
        extractDate(date);
    }
    public void extractDate(String date){
        String[] extractedDate = date.split("/", -1);
        month = Integer.parseInt(extractedDate[0]);
        day = Integer.parseInt(extractedDate[1]);
        year = Integer.parseInt(extractedDate[2]);
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
    public String getNote() {return note;}
    public String getDate() {return date;}
    public String getCategory() {return category;}
}
