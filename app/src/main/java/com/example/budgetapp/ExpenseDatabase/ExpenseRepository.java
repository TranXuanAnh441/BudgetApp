package com.example.budgetapp.ExpenseDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.budgetapp.AppDatabase;

import java.util.List;

public class ExpenseRepository {
    private ExpenseDao expenseDao;
    private LiveData<List<Expense>> allExpense;
    private LiveData<List<Expense>> dateExpense;
    private LiveData<Integer> dateSum;

    public ExpenseRepository(Application application){
        AppDatabase expenseDatabase = AppDatabase.getInstance(application);
        expenseDao = expenseDatabase.expenseDao();
        allExpense = expenseDao.getAllExpense();
    }

    public void insert(Expense expense){
        new InsertExpenseAsyncTask(expenseDao).execute(expense);
    };
    public void update(Expense expense){
        new UpdateExpenseAsyncTask(expenseDao).execute(expense);
    };
    public void delete(Expense expense){
        new DeleteExpenseAsyncTask(expenseDao).execute(expense);
    };
    public void deleteAll(){
        new DeleteAllExpenseAsyncTask(expenseDao).execute();
    };

    public LiveData<List<Expense>> getAllExpense(){
        return allExpense;
    }

    public LiveData<Integer> getDateSum(String v) {
        dateSum = expenseDao.getDateSum(v);
        return dateSum;
    }

    public LiveData<List<Expense>> getDateExpense(String v){
        dateExpense = expenseDao.getDateExpense(v);
        return dateExpense;
    }

    private static class InsertExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDao expenseDao;
        private InsertExpenseAsyncTask(ExpenseDao expenseDao){
            this.expenseDao = expenseDao;
        }
        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.insert(expenses[0]);
            return null;
        }
    }
    private static class UpdateExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDao expenseDao;
        private UpdateExpenseAsyncTask(ExpenseDao expenseDao){
            this.expenseDao = expenseDao;
        }
        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.update(expenses[0]);
            return null;
        }
    }
    private static class DeleteExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDao expenseDao;
        private DeleteExpenseAsyncTask(ExpenseDao expenseDao){
            this.expenseDao = expenseDao;
        }
        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.delete(expenses[0]);
            return null;
        }
    }
    private static class DeleteAllExpenseAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExpenseDao expenseDao;
        private DeleteAllExpenseAsyncTask(ExpenseDao expenseDao){
            this.expenseDao = expenseDao;
        }
        @Override
        protected Void doInBackground(Void...voids) {
            expenseDao.deleteAll();
            return null;
        }
    }
}
