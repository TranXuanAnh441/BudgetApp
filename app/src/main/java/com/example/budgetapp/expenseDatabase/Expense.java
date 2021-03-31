package com.example.budgetapp.expenseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_table")
public class Expense {
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    @PrimaryKey(autoGenerate = true)
    private int eid;

    private String title;

    private String description;

    private int amount;

    private String date;

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    @Nullable
    private int categoryId;

    public Expense(String title, String description, int amount, String date) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
