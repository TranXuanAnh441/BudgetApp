package com.example.budgetapp.CategoryExpense;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.budgetapp.categoryDatabase.Category;
import com.example.budgetapp.expenseDatabase.Expense;

import java.util.List;

@Dao
public interface CategoryExpenseDao {
    @Transaction
    @Query("SELECT * from expense_table WHERE categoryId=:cid")
    Expense findExpense(long cid);

    @Query("SELECT * from category_table WHERE cid=:categoryId")
    Category findCategory(long categoryId);


}
