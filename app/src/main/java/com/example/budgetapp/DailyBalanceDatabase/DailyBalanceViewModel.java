package com.example.budgetapp.DailyBalanceDatabase;

public class DailyBalanceViewModel {
    private DailyBalanceRepository dailyBalanceRepository;


    public DailyBalanceViewModel(DailyBalanceRepository dailyBalanceRepository) {
        this.dailyBalanceRepository = dailyBalanceRepository;
    }
    public void insert(DailyBalance dailyBalance){dailyBalanceRepository.insert(dailyBalance);}
    public void update(DailyBalance dailyBalance){dailyBalanceRepository.update(dailyBalance);}
    public void delete(DailyBalance dailyBalance){dailyBalanceRepository.update(dailyBalance);}
}
