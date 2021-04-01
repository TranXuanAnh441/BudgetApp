package com.example.budgetapp.CategoryExpense;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.budgetapp.categoryDatabase.Category;
import com.example.budgetapp.expenseDatabase.Expense;

import java.util.List;

public class CategoryExpense {

    @Embedded
    public Category category;
    @Relation(
            parentColumn = "cid",
            entityColumn = "categoryId"
    )
    public List<Expense> expenses;

    public CategoryExpense(Category category, List<Expense> expenses) {
        this.category = category;
        this.expenses = expenses;
    }

}
