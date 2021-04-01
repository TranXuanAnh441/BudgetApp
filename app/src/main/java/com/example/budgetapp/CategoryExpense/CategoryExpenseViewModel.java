package com.example.budgetapp.CategoryExpense;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class CategoryExpenseViewModel extends AndroidViewModel {
    private CategoryExpenseRepository categoryExpenseRepository;

    public CategoryExpenseViewModel(@NonNull Application application) {
        super(application);
        categoryExpenseRepository = new CategoryExpenseRepository(application);
    }
    public void InsertCategoryWithExpense(CategoryExpense categoryExpense){
        categoryExpenseRepository.insert(categoryExpense);
    }
}
