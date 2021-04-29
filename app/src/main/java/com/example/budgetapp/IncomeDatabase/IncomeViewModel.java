package com.example.budgetapp.IncomeDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

public class IncomeViewModel extends AndroidViewModel {
    private IncomeRepository incomeRepository;
    private LiveData<List<Income>> allIncome;
    private LiveData<List<Income>> dateIncome;
    private LiveData<Integer> dateSum;
    private LiveData<Integer> monthSum;
    private MutableLiveData<String> filterLiveData = new MutableLiveData<>();
    private MutableLiveData<String> monthLiveData = new MutableLiveData<>();
    public IncomeViewModel(@NonNull Application application) {
        super(application);
        incomeRepository = new IncomeRepository(application);
        allIncome = incomeRepository.getAllIncome();
        dateIncome = Transformations.switchMap(filterLiveData,
                v -> incomeRepository.getDateIncome(v));
        dateSum = Transformations.switchMap(filterLiveData,
                v -> incomeRepository.getDateSum(v));
        monthSum = Transformations.switchMap(monthLiveData,
                v -> incomeRepository.getMonthSum(v));
    }

    public LiveData<List<Income>> getDateIncome() { return dateIncome; }
    public LiveData<Integer> getDateSum(){return dateSum;}
    public LiveData<Integer> getMonthSum(){return monthSum;}
    public void setMonthFilter(String filter) { monthLiveData.setValue(filter);}
    public void setFilter(String filter) { filterLiveData.setValue(filter);}
    public void insert(Income income ){
        incomeRepository.insert(income);
    }
    public void update(Income income ){
        incomeRepository.update(income);
    }
    public void delete(Income income ){
        incomeRepository.delete(income);
    }
    public void deleteAll(){
        incomeRepository.deleteAll();
    }
    public LiveData<List<Income>> getAllIncome(){
        return allIncome;
    }
}
