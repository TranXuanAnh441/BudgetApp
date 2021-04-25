package com.example.budgetapp.DailyBalanceDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.budgetapp.AppDatabase;

public class DailyBalanceRepository {
    private DailyBalanceDao dailyBalanceDao;
    private LiveData<DailyBalance> dateBalance;
    private LiveData<Boolean> checkBalance;

    public DailyBalanceRepository(Application application){
        AppDatabase balanceDatabase = AppDatabase.getInstance(application);
        dailyBalanceDao = balanceDatabase.dailyBalanceDao();
    }
    public void insert(DailyBalance dailyBalance){
        new InsertBalanceAsyncTask(dailyBalanceDao).execute(dailyBalance);
    };
    public void update(DailyBalance dailyBalance){
        new UpdateBalanceAsyncTask(dailyBalanceDao).execute(dailyBalance);
    };
    public void delete(DailyBalance dailyBalance){
        new DeleteBalanceAsyncTask(dailyBalanceDao).execute(dailyBalance);
    };
    public LiveData<DailyBalance> getDateBalance(String v){
        dateBalance = dailyBalanceDao.getDateBalance(v);
        return dateBalance;
    }
    public LiveData<Boolean> getCheckBalance(String v){
        checkBalance = dailyBalanceDao.checkBalance(v);
        return checkBalance;
    }

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
