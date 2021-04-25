package com.example.budgetapp.DailyBalanceDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DailyBalanceDao {
    @Insert
    void insert(DailyBalance dailyBalance);
    @Update
    void update(DailyBalance dailyBalance);
    @Delete
    void delete(DailyBalance dailyBalance);
    @Query("SELECT * FROM balance_table WHERE date =:date")
    LiveData<DailyBalance> getDateBalance(String date);
    @Query("SELECT EXISTS (SELECT * FROM balance_table WHERE date = :date)")
    LiveData<Boolean> checkBalance(String date);
}
