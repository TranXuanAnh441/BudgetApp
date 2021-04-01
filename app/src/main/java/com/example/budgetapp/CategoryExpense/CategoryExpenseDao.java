package com.example.budgetapp.CategoryExpense;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Transaction;

import com.example.budgetapp.categoryDatabase.Category;
import com.example.budgetapp.expenseDatabase.Expense;

import java.util.List;

@Dao
public interface CategoryExpenseDao {

    @Transaction
    @Insert
    long insertCategory(Category category);

    @Insert
    void insertExpense(List<Expense> expenses);


}
