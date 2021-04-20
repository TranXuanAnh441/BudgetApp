package com.example.budgetapp.DailyBalanceDatabase;

import android.app.Application;
import android.os.AsyncTask;
import com.example.budgetapp.AppDatabase;

public class DailyBalanceRepository {
    DailyBalanceDao dailyBalanceDao;

    public DailyBalanceRepository(Application application){
        AppDatabase balanceDatabase = AppDatabase.getInstance(application);
        dailyBalanceDao = balanceDatabase.dailyBalanceDao();
    }
    public void insert(DailyBalance dailyBalance){
        new InsertBalanceAsyncTask(dailyBalanceDao).execute(dailyBalance);
    };
    public void update(DailyBalance dailyBalance){
        new InsertBalanceAsyncTask(dailyBalanceDao).execute(dailyBalance);
    };
    public void delete(DailyBalance dailyBalance){
        new InsertBalanceAsyncTask(dailyBalanceDao).execute(dailyBalance);
    };

    public static class InsertBalanceAsyncTask extends AsyncTask<DailyBalance, Void, Void> {
        DailyBalanceDao dailyBalanceDao;
        public InsertBalanceAsyncTask(DailyBalanceDao dailyBalanceDao) {
            this.dailyBalanceDao = dailyBalanceDao;
        }
        @Override
        protected Void doInBackground(DailyBalance... dailyBalances) {
            dailyBalanceDao.insert(dailyBalances[0]);
            return null;
        }
    }
    public static class UpdateBalanceAsyncTask extends AsyncTask<DailyBalance, Void, Void> {
        DailyBalanceDao dailyBalanceDao;
        public UpdateBalanceAsyncTask(DailyBalanceDao dailyBalanceDao) {
            this.dailyBalanceDao = dailyBalanceDao;
        }
        @Override
        protected Void doInBackground(DailyBalance... dailyBalances) {
            dailyBalanceDao.update(dailyBalances[0]);
            return null;
        }
    }
    public static class DeleteBalanceAsyncTask extends AsyncTask<DailyBalance, Void, Void> {
        DailyBalanceDao dailyBalanceDao;
        public DeleteBalanceAsyncTask(DailyBalanceDao dailyBalanceDao) {
            this.dailyBalanceDao = dailyBalanceDao;
        }
        @Override
        protected Void doInBackground(DailyBalance... dailyBalances) {
            dailyBalanceDao.delete(dailyBalances[0]);
            return null;
        }
    }
}
