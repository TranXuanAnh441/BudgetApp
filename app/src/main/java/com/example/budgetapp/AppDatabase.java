package com.example.budgetapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.budgetapp.CategoryExpense.CategoryExpense;
import com.example.budgetapp.CategoryExpense.CategoryExpenseDao;
import com.example.budgetapp.categoryDatabase.Category;
import com.example.budgetapp.categoryDatabase.CategoryDao;
import com.example.budgetapp.expenseDatabase.Expense;
import com.example.budgetapp.expenseDatabase.ExpenseDao;

@Database(entities = {Expense.class, Category.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static com.example.budgetapp.AppDatabase instance;

    public abstract CategoryDao categoryDao();

    public abstract ExpenseDao expenseDao();

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


