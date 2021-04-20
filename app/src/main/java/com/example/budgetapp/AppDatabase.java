package com.example.budgetapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.budgetapp.CategoryExpense.CategoryExpenseDao;
import com.example.budgetapp.CategoryDatabase.Category;
import com.example.budgetapp.CategoryDatabase.CategoryDao;
import com.example.budgetapp.ExpenseDatabase.Expense;
import com.example.budgetapp.ExpenseDatabase.ExpenseDao;
import com.example.budgetapp.IncomeDatabase.Income;
import com.example.budgetapp.IncomeDatabase.IncomeDao;

@Database(entities = {Expense.class, Category.class, Income.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    private static com.example.budgetapp.AppDatabase instance;

    public abstract CategoryDao categoryDao();

    public abstract ExpenseDao expenseDao();

    public abstract IncomeDao incomeDao();

    public abstract CategoryExpenseDao categoryExpenseDao();

    public static synchronized com.example.budgetapp.AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), com.example.budgetapp.AppDatabase.class, "app_database").
                    fallbackToDestructiveMigration().
                    build();
        }
        return instance;
    }

   }


