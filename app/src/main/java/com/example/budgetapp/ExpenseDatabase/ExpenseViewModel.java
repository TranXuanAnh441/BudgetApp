package com.example.budgetapp.ExpenseDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseRepository expenseRepository;
    private LiveData<List<Expense>> allExpense;
    private LiveData<List<Expense>> dateExpense;
    private LiveData<Integer> dateSum;
    private MutableLiveData<String> filterLiveData = new MutableLiveData<>();

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        expenseRepository = new ExpenseRepository(application);
        allExpense = expenseRepository.getAllExpense();
        dateExpense = Transformations.switchMap(filterLiveData,
                v -> expenseRepository.getDateExpense(v));
        dateSum = Transformations.switchMap(filterLiveData,
                v -> expenseRepository.getDateSum(v));
    }
    public LiveData<Integer> getDateSum() { return dateSum; }
    public LiveData<List<Expense>> getDateExpense() { return dateExpense; }
    public void setFilter(String filter) { filterLiveData.setValue(filter);}
    public void insert(Expense expense){
        expenseRepository.insert(expense);
    }
    public void update(Expense expense){
        expenseRepository.update(expense);
    }
    public void delete(Expense expense){
        expenseRepository.delete(expense);
    }
    public void deleteAll(){
        expenseRepository.deleteAll();
    }
    public LiveData<List<Expense>> getAllExpense(){
        return allExpense;
    }

    }


