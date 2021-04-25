package com.example.budgetapp.IncomeDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface IncomeDao {
    @Insert
    void insert(Income income);
    @Update
    void update(Income income);
    @Delete
    void delete(Income expense);
    @Query("DELETE FROM income_table")
    void deleteAll();
    @Query("SELECT * FROM income_table")
    LiveData<List<Income>> getAllIncome();
    @Query("SELECT * FROM income_table WHERE date=:date")
    LiveData<List<Income>> getDateIncome(String date);
    @Query("SELECT SUM(amount) FROM income_table WHERE date=:date")
    LiveData<Integer> getDateSum(String date);
}
