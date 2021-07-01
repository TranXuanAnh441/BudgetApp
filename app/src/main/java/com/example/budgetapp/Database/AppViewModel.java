package com.example.budgetapp.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.anychart.chart.common.dataentry.DataEntry;
import com.example.budgetapp.Database.Category.Category;
import com.example.budgetapp.Database.Category.CategoryRepository;
import com.example.budgetapp.Database.ExpenseIncome.ExpenseIncome;
import com.example.budgetapp.Database.ExpenseIncome.ExpenseIncomeRepository;


import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private ExpenseIncomeRepository expenseIncomeRepository;
    private LiveData<List<ExpenseIncome>> allExpense;
    private LiveData<List<ExpenseIncome>> dateExpense;
    private LiveData<Integer> sumMonthExpense;
    private LiveData<Integer> sumDateExpense;
    private LiveData<List<ExpenseIncome>> allIncome;
    private LiveData<List<ExpenseIncome>> dateIncome;
    private LiveData<Integer> sumDateIncome;
    private LiveData<Integer> sumMonthIncome;
    private MutableLiveData<String> dateLiveData = new MutableLiveData<>();
    private MutableLiveData<String> monthLiveData = new MutableLiveData<>();
    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> allCategory;
    private MutableLiveData<Category> findCategoryById;
    private MutableLiveData<List<DataEntry>> dataEntries;


    public AppViewModel(@NonNull Application application) {
        super(application);
        expenseIncomeRepository = new  ExpenseIncomeRepository(application);
        allExpense = expenseIncomeRepository.getAllExpense();
        dateExpense = Transformations.switchMap(dateLiveData,
                v -> expenseIncomeRepository.getDateExpense(v));
        sumDateExpense = Transformations.switchMap(dateLiveData,
                v -> expenseIncomeRepository.getSumDateExpense(v));
        sumMonthExpense = Transformations.switchMap(monthLiveData,
                v -> expenseIncomeRepository.getSumMonthExpense(v));
        allIncome = expenseIncomeRepository.getAllIncome();
        dateIncome = Transformations.switchMap(dateLiveData,
                v -> expenseIncomeRepository.getDateIncome(v));
        sumDateIncome = Transformations.switchMap(dateLiveData,
                v -> expenseIncomeRepository.getSumDateIncome(v));
        sumMonthIncome = Transformations.switchMap(monthLiveData,
                v -> expenseIncomeRepository.getSumMonthExpense(v));
        categoryRepository = new CategoryRepository(application);
        allCategory = categoryRepository.getAllCategory();
        findCategoryById = categoryRepository.getCategoryById();
        dataEntries = categoryRepository.getMonthPie();

    }
    public LiveData<Integer> getSumDateExpense() { return sumDateExpense; }
    public LiveData<Integer> getSumDateIncome() { return sumDateIncome; }

    public LiveData<List<ExpenseIncome>> getAllExpense(){
        return allExpense;
    }
    public LiveData<List<ExpenseIncome>> getDateExpense(){
        return dateExpense;
    }

    public LiveData<List<ExpenseIncome>> getAllIncome(){ return allIncome;}
    public LiveData<List<ExpenseIncome>> getDateIncome(){
        return dateIncome;
    }


    public LiveData<Integer> getSumMonthExpense(){ return sumMonthExpense;}
    public LiveData<Integer> getSumMonthIncome(){ return sumMonthIncome;}

    public void setMonthFilter(String filter) { monthLiveData.setValue(filter);}
    public void setDateFilter(String filter) { dateLiveData.setValue(filter);}


    public void insertExpenseIncome(ExpenseIncome expenseIncome){
        expenseIncomeRepository.insertExpenseIncome(expenseIncome);
    }
    public void updateExpenseIncome(ExpenseIncome expenseIncome){
        expenseIncomeRepository.updateExpenseIncome(expenseIncome);
    }
    public void deleteExpenseIncome(ExpenseIncome expenseIncome){
        expenseIncomeRepository.deleteExpenseIncome(expenseIncome);
    }
    public void deleteAllExpense(){
        expenseIncomeRepository.deleteAllExpense();
    }
    public void deleteAllIncome(){
        expenseIncomeRepository.deleteAllIncome();
    }

    public MutableLiveData<Category> getCategoryResult() { return findCategoryById; }
    public MutableLiveData<List<DataEntry>> getMonthPieResult(){return dataEntries; }
    public LiveData<List<Category>> getAllCategory(){ return allCategory; }
    public void findCategoryId(Integer id ){ categoryRepository.findCategory(id);}
    public void insertCategory(Category category){
        categoryRepository.insert(category);
    }
    public void updateCategory(Category category){
        categoryRepository.update(category);
    }
    public void deleteCategory(Category category){
        categoryRepository.delete(category);
    }
    public void getMonthPie(String date){categoryRepository.getMonthPie(date);}
}
