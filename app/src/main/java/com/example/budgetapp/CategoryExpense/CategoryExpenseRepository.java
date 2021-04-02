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
    public void insert(CategoryExpense categoryExpense) {
        new CategoryExpenseAsyncTask(categoryExpenseDao).execute(categoryExpense);
    }


    private static class CategoryExpenseAsyncTask extends AsyncTask<CategoryExpense, Void, Void >{
        CategoryExpenseDao categoryExpenseDao;
        public CategoryExpenseAsyncTask(CategoryExpenseDao categoryExpenseDao) { this.categoryExpenseDao = categoryExpenseDao; }
        @Override
        protected Void doInBackground(CategoryExpense... categoryExpenses) {
            for (Expense expense : categoryExpenses[0].expenses) {
                expense.setCategoryId(categoryExpenses[0].category.getCid());
            }
            return null;
    }
}}
