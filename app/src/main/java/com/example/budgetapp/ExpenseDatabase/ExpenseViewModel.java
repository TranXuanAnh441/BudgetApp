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
    private LiveData<Integer> monthExpense;
    private LiveData<Integer> dateSum;
    private MutableLiveData<String> dateLiveData = new MutableLiveData<>();
    private MutableLiveData<String> monthLiveData = new MutableLiveData<>();

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        expenseRepository = new ExpenseRepository(application);
        allExpense = expenseRepository.getAllExpense();
        dateExpense = Transformations.switchMap(dateLiveData,
                v -> expenseRepository.getDateExpense(v));
        dateSum = Transformations.switchMap(dateLiveData,
                v -> expenseRepository.getDateSum(v));
        monthExpense = Transformations.switchMap(monthLiveData,
                v -> expenseRepository.getMonthExpense(v));

    }
    public LiveData<Integer> getDateSum() { return dateSum; }
    public LiveData<List<Expense>> getAllExpense(){
        return allExpense;
    }
    public LiveData<List<Expense>> getDateExpense(){ return dateExpense;}
    public LiveData<Integer> getMonthSum(){ return monthExpense;}
    public void setMonthFilter(String filter) { monthLiveData.setValue(filter);}
    public void setFilter(String filter) { dateLiveData.setValue(filter);}
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
    }


