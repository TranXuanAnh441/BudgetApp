package com.example.budgetapp.DailyBalanceDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.budgetapp.AppDatabase;

public class DailyBalanceViewModel extends AndroidViewModel {
    private DailyBalanceRepository dailyBalanceRepository;
    private LiveData<DailyBalance> dateBalance;
    private LiveData<Boolean> checkBalance;
    private MutableLiveData<String> filterDateData = new MutableLiveData<>();

    public DailyBalanceViewModel(@NonNull Application application) {
        super(application);
        dailyBalanceRepository = new DailyBalanceRepository(application);
        dateBalance =  Transformations.switchMap(filterDateData, v -> dailyBalanceRepository.getDateBalance(v));
        checkBalance =  Transformations.switchMap(filterDateData, v -> dailyBalanceRepository.getCheckBalance(v));}

    public void insert(DailyBalance dailyBalance){dailyBalanceRepository.insert(dailyBalance);}
    public void update(DailyBalance dailyBalance){dailyBalanceRepository.update(dailyBalance);}
    public void delete(DailyBalance dailyBalance){dailyBalanceRepository.update(dailyBalance);}
    public LiveData<DailyBalance> getDateBalance(){return dateBalance;}
    public LiveData<Boolean> getCheckBalance(){return checkBalance;}
    public void setDateFilter(String dateFilter) { filterDateData.setValue(dateFilter);}

}
