package com.cheekupeeku.expensetracker.model;

public class Expense {
   private int id;
   private int categoryId;
   private String tag;
   private String edate;
   private int amount;
   private String paymentMode;
   private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Expense(int id, int categoryId, String tag, String edate, int amount, String paymentMode) {
        this.id = id;
        this.categoryId = categoryId;
        this.tag = tag;
        this.edate = edate;
        this.amount = amount;
        this.paymentMode = paymentMode;
    }
    public Expense(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}
