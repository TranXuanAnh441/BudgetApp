package com.example.budgetapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.budgetapp.Database.AppDao;
import com.example.budgetapp.Database.Category.Category;
import com.example.budgetapp.Database.ExpenseIncome.ExpenseIncome;

@Database(entities = {ExpenseIncome.class, Category.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static com.example.budgetapp.AppDatabase instance;
    public abstract AppDao appDao();

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
        private AppDao categoryDao;
        private PopulateDbAsyncTask(AppDatabase db) {
            categoryDao = db.appDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            categoryDao.insertCategory(new Category("Renting"));
            categoryDao.insertCategory(new Category("Eating"));
            categoryDao.insertCategory(new Category("Transport"));
            categoryDao.insertCategory(new Category("Entertainment"));
            categoryDao.insertCategory(new Category("Health care"));
            categoryDao.insertCategory(new Category("Education"));
            return null;
        }
    }

   }


