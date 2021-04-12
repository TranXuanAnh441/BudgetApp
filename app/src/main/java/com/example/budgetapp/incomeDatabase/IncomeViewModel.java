package com.example.budgetapp.incomeDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.budgetapp.expenseDatabase.Expense;

import java.util.List;

public class IncomeViewModel extends AndroidViewModel {
    private IncomeRepository incomeRepository;
    private LiveData<List<Income>> allIncome;
    private LiveData<List<Income>> dateIncome;
    private MutableLiveData<String> filterLiveData = new MutableLiveData<>();
    public IncomeViewModel(@NonNull Application application) {
        super(application);
        incomeRepository = new IncomeRepository(application);
        allIncome = incomeRepository.getAllIncome();
        dateIncome = Transformations.switchMap(filterLiveData,
                v -> incomeRepository.getDateIncome(v));
    }

    public LiveData<List<Income>> getDateIncome() { return dateIncome; }
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
