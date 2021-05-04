package com.example.budgetapp.Database.ExpenseIncome;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.budgetapp.AppDatabase;
import com.example.budgetapp.Database.AppDao;


import java.util.List;

public class ExpenseIncomeRepository {
    private AppDao appDao;
    private LiveData<List<ExpenseIncome>> allExpense;
    private LiveData<List<ExpenseIncome>> dateExpense;
    private LiveData<List<ExpenseIncome>> allIncome;
    private LiveData<List<ExpenseIncome>> dateIncome;
    private LiveData<Integer> expenseSumDate;
    private LiveData<Integer> incomeSumDate;
    private LiveData<Integer> expenseSumMonth;

    public ExpenseIncomeRepository(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        appDao = appDatabase.appDao();
        allExpense = appDao.getAllExpense();
        allIncome = appDao.getAllIncome();
    }

    public void insertExpenseIncome(ExpenseIncome expenseIncome){
        new ExpenseIncomeRepository.InsertExpenseIncomeAsyncTask(appDao).execute(expenseIncome);
    };
    public void updateExpenseIncome(ExpenseIncome expenseIncome){
        new ExpenseIncomeRepository.UpdateExpenseIncomeAsyncTask(appDao).execute(expenseIncome);
    };
    public void deleteExpenseIncome(ExpenseIncome expenseIncome){
        new ExpenseIncomeRepository.DeleteExpenseIncomeAsyncTask(appDao).execute(expenseIncome);
    };
    public void deleteAllExpense(){
        new ExpenseIncomeRepository.DeleteAllExpenseAsyncTask(appDao).execute();
    };
    public void deleteAllIncome(){
        new ExpenseIncomeRepository.DeleteAllIncomeAsyncTask(appDao).execute();
    };

    public LiveData<List<ExpenseIncome>> getAllExpense(){
        return allExpense;
    }
    public LiveData<List<ExpenseIncome>> getAllIncome(){
        return allIncome;
    }

    public LiveData<Integer> getSumDateExpense(String v) {
        expenseSumDate = appDao.getSumDateExpense(v);
        return expenseSumDate;
    }

    public LiveData<Integer> getSumDateIncome(String v) {
        incomeSumDate = appDao.getSumDateIncome(v);
        return incomeSumDate;
    }

    public LiveData<List<ExpenseIncome>> getDateExpense(String v){
        dateExpense = appDao.getDateExpense(v);
        return dateExpense;
    }
    public LiveData<List<ExpenseIncome>> getDateIncome(String v){
        dateIncome = appDao.getDateIncome(v);
        return dateIncome;
    }

    public LiveData<Integer> getSumMonthExpense(String v){
        expenseSumMonth = appDao.getSumMonthExpense(v);
        return expenseSumMonth;
    }

    private static class InsertExpenseIncomeAsyncTask extends AsyncTask<ExpenseIncome, Void, Void> {
        private AppDao appDao;
        private InsertExpenseIncomeAsyncTask(AppDao appDao){
            this.appDao = appDao;
        }
        @Override
        protected Void doInBackground(ExpenseIncome... expenseIncomes) {
            appDao.insertExpenseIncome(expenseIncomes[0]);
            return null;
        }
    }

    private static class UpdateExpenseIncomeAsyncTask extends AsyncTask<ExpenseIncome, Void, Void> {
        private AppDao appDao;
        private UpdateExpenseIncomeAsyncTask(AppDao appDao){
            this.appDao = appDao;
        }
        @Override
        protected Void doInBackground(ExpenseIncome... expenseIncomes) {
            appDao.updateExpenseIncome(expenseIncomes[0]);
            return null;
        }
    }

    private static class DeleteExpenseIncomeAsyncTask extends AsyncTask<ExpenseIncome, Void, Void> {
        private AppDao appDao;
        private DeleteExpenseIncomeAsyncTask(AppDao appDao){
            this.appDao = appDao;
        }
        @Override
        protected Void doInBackground(ExpenseIncome... expenseIncomes) {
            appDao.deleteExpenseIncome(expenseIncomes[0]);
            return null;
        }
    }

    private static class DeleteAllExpenseAsyncTask extends AsyncTask<ExpenseIncome, Void, Void> {
        private AppDao appDao;
        private DeleteAllExpenseAsyncTask(AppDao appDao){
            this.appDao = appDao;
        }
        @Override
        protected Void doInBackground(ExpenseIncome... expenseIncomes) {
            appDao.deleteAllExpense();
            return null;
        }
    }

    private static class DeleteAllIncomeAsyncTask extends AsyncTask<ExpenseIncome, Void, Void> {
        private AppDao appDao;
        private DeleteAllIncomeAsyncTask(AppDao appDao){
            this.appDao = appDao;
        }
        @Override
        protected Void doInBackground(ExpenseIncome... expenseIncomes) {
            appDao.deleteAllIncome();
            return null;
        }
    }

}
