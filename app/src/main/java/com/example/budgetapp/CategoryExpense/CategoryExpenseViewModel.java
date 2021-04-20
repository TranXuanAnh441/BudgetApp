package com.example.budgetapp.CategoryExpense;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.budgetapp.CategoryDatabase.Category;

public class CategoryExpenseViewModel extends AndroidViewModel {
    private CategoryExpenseRepository categoryExpenseRepository;
    private Category category;
    public CategoryExpenseViewModel(@NonNull Application application) {
        super(application);
        categoryExpenseRepository = new CategoryExpenseRepository(application);
    }
}
