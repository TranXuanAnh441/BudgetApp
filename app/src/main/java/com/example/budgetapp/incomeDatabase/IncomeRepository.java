package com.example.budgetapp.incomeDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.example.budgetapp.AppDatabase;
import com.example.budgetapp.expenseDatabase.Expense;
import com.example.budgetapp.expenseDatabase.ExpenseDao;

import java.util.List;

public class IncomeRepository {
    private IncomeDao incomeDao;
    private LiveData<List<Income>> allIncome;
    private LiveData<List<Income>> dateIncome;

    public IncomeRepository(Application application){
        AppDatabase incomeDatabase = AppDatabase.getInstance(application);
        incomeDao = incomeDatabase.incomeDao();
        allIncome = incomeDao.getAllIncome();
    }

    public void insert(Income income){
        new com.example.budgetapp.incomeDatabase.IncomeRepository.InsertIncomeAsyncTask(incomeDao).execute(income);
    };
    public void update(Income income){
        new com.example.budgetapp.incomeDatabase.IncomeRepository.UpdateIncomeAsyncTask(incomeDao).execute(income);
    };
    public void delete(Income income){
        new com.example.budgetapp.incomeDatabase.IncomeRepository.DeleteIncomeAsyncTask(incomeDao).execute(income);
    };

    public void deleteAll(){
        new com.example.budgetapp.incomeDatabase.IncomeRepository.DeleteAllIncomeAsyncTask(incomeDao).execute();
    };

    public LiveData<List<Income>> getAllIncome(){
        return allIncome;
    }

    public LiveData<List<Income>> getDateIncome(String v){
        dateIncome = incomeDao.getDateIncome(v);
        return dateIncome;
    }

    private static class InsertIncomeAsyncTask extends AsyncTask<Income, Void, Void> {
        private IncomeDao incomeDao;
        private InsertIncomeAsyncTask(IncomeDao incomeDao){
            this.incomeDao = incomeDao;
        }
        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDao.insert(incomes[0]);
            return null;
        }
    }
    private static class UpdateIncomeAsyncTask extends AsyncTask<Income, Void, Void> {
        private IncomeDao incomeDao;
        private UpdateIncomeAsyncTask(IncomeDao incomeDao){
            this.incomeDao = incomeDao;
        }
        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDao.update(incomes[0]);
            return null;
        }
    }
    private static class DeleteIncomeAsyncTask extends AsyncTask<Income, Void, Void> {
        private IncomeDao incomeDao;
        private DeleteIncomeAsyncTask(IncomeDao incomeDao){
            this.incomeDao = incomeDao;
        }
        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDao.delete(incomes[0]);
            return null;
        }
    }
    private static class  DeleteAllIncomeAsyncTask extends AsyncTask<Void, Void, Void> {
        private IncomeDao incomeDao;
        private DeleteAllIncomeAsyncTask(IncomeDao incomeDao){
            this.incomeDao = incomeDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            incomeDao.deleteAll();
            return null;
        }
    }
}
