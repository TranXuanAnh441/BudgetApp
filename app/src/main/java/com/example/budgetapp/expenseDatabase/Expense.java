package com.example.budgetapp.expenseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.budgetapp.categoryDatabase.Category;

import static androidx.room.ForeignKey.CASCADE;

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

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    @Nullable
    private long categoryId;

    @ForeignKey(entity = Category.class,
            parentColumns = "cid",
            childColumns = "categoryId",
            onDelete = CASCADE
    )

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
