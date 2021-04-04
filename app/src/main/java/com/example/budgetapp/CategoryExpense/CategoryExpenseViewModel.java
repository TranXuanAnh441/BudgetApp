package com.example.budgetapp.CategoryExpense;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.budgetapp.categoryDatabase.Category;
import com.example.budgetapp.expenseDatabase.Expense;

public class CategoryExpenseViewModel extends AndroidViewModel {
    private CategoryExpenseRepository categoryExpenseRepository;
    private Category category;
    public CategoryExpenseViewModel(@NonNull Application application) {
        super(application);
        categoryExpenseRepository = new CategoryExpenseRepository(application);
    }
}
