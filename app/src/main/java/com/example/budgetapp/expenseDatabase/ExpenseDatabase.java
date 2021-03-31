package com.example.budgetapp.expenseDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.budgetapp.categoryDatabase.Category;

@Database(entities = {Expense.class, Category.class }, version = 2)
public abstract class ExpenseDatabase extends RoomDatabase {
    private static ExpenseDatabase instance;

    public abstract ExpenseDao expenseDao();

    public static synchronized ExpenseDatabase getInstance(Context context){
        if (instance == null){
           instance = Room.databaseBuilder(context.getApplicationContext(), ExpenseDatabase.class, "expense_database").fallbackToDestructiveMigration().build();
        }
    return instance;
    }

}
