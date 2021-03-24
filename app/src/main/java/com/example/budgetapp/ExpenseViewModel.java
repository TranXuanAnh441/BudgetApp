package com.example.budgetapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.budgetapp.database.Expense;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseRepository expenseRepository;
    private LiveData<List<Expense>> allExpense;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        expenseRepository = new ExpenseRepository(application);
        allExpense = expenseRepository.getAllExpense();
    }
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
