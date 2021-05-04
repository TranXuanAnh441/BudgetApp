package com.example.budgetapp.Database.ExpenseIncome;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_income_table")
public class ExpenseIncome {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

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

    public int getTypeId() {
        return typeId;
    }

    private int typeId;

    public ExpenseIncome(String title, String description, int amount, String date, int typeId) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.typeId = typeId;
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

