package com.example.budgetapp.CategoryExpense;
import android.app.Application;
import android.os.AsyncTask;
import com.example.budgetapp.AppDatabase;
import com.example.budgetapp.expenseDatabase.Expense;

public class CategoryExpenseRepository {
    private CategoryExpenseDao categoryExpenseDao;

    public CategoryExpenseRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.categoryExpenseDao = database.categoryExpenseDao();
    }
}
