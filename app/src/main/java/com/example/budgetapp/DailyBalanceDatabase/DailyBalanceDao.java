package com.example.budgetapp.DailyBalanceDatabase;

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
    void getDateBalance(String date);
}
