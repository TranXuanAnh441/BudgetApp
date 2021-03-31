package com.example.budgetapp.categoryDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.budgetapp.expenseDatabase.Expense;


@Database(entities = {Expense.class, Category.class}, version = 2)
public abstract class CategoryDatabase extends RoomDatabase {
    private static CategoryDatabase instance;

    public abstract CategoryDao categoryDao();

    public static synchronized CategoryDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), CategoryDatabase.class, "category_database").
                    fallbackToDestructiveMigration().
                    build();
        }
        return instance;
    }}

