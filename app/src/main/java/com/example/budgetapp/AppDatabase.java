package com.example.budgetapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.budgetapp.CategoryExpense.CategoryExpenseDao;
import com.example.budgetapp.CategoryDatabase.Category;
import com.example.budgetapp.CategoryDatabase.CategoryDao;
import com.example.budgetapp.DailyBalanceDatabase.DailyBalance;
import com.example.budgetapp.DailyBalanceDatabase.DailyBalanceDao;
import com.example.budgetapp.ExpenseDatabase.Expense;
import com.example.budgetapp.ExpenseDatabase.ExpenseDao;
import com.example.budgetapp.IncomeDatabase.Income;
import com.example.budgetapp.IncomeDatabase.IncomeDao;

@Database(entities = {Expense.class, Category.class, Income.class, DailyBalance.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    private static com.example.budgetapp.AppDatabase instance;

    public abstract CategoryDao categoryDao();

    public abstract ExpenseDao expenseDao();

    public abstract IncomeDao incomeDao();

    public abstract DailyBalanceDao dailyBalanceDao();

    public abstract CategoryExpenseDao categoryExpenseDao();

    public static synchronized com.example.budgetapp.AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), com.example.budgetapp.AppDatabase.class, "app_database").
                    fallbackToDestructiveMigration().addCallback(roomCallback).
                    build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private CategoryDao categoryDao;
        private PopulateDbAsyncTask(AppDatabase db) {
            categoryDao = db.categoryDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            categoryDao.insert(new Category("Renting"));
            categoryDao.insert(new Category("Eating"));
            categoryDao.insert(new Category("Transport"));
            categoryDao.insert(new Category("Entertainment"));
            categoryDao.insert(new Category("Health care"));
            categoryDao.insert(new Category("Education"));
            return null;
        }
    }

   }


