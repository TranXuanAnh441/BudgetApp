package com.example.budgetapp.CategoryExpense;
import android.app.Application;

import com.example.budgetapp.AppDatabase;

public class CategoryExpenseRepository {
    private CategoryExpenseDao categoryExpenseDao;

    public CategoryExpenseRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.categoryExpenseDao = database.categoryExpenseDao();
    }
}
