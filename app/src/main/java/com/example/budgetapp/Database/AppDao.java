package com.example.budgetapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.budgetapp.Database.Category.Category;
import com.example.budgetapp.Database.ExpenseIncome.ExpenseIncome;

import java.util.List;

@Dao
public interface AppDao {
    //ExpenseIncome
    @Insert
    void insertExpenseIncome(ExpenseIncome expenseIncome);
    @Update
    void updateExpenseIncome(ExpenseIncome expenseIncome);
    @Delete
    void deleteExpenseIncome(ExpenseIncome expenseIncome);

    //Expense
    @Query("DELETE FROM expense_income_table where typeId = 1")
    void deleteAllExpense();

    @Query("SELECT * FROM expense_income_table where typeId = 1")
    LiveData<List<ExpenseIncome>> getAllExpense();

    @Query("SELECT * FROM expense_income_table WHERE date=:date and typeId = 1")
    LiveData<List<ExpenseIncome>> getDateExpense(String date);

    @Query("Select SUM(amount) FROM expense_income_table where date=:date and typeId =1")
    LiveData<Integer> getSumDateExpense(String date);

    @Query("Select SUM(amount) FROM expense_income_table where date LIKE :date and typeId = 1")
    LiveData<Integer> getSumMonthExpense(String date);

    //Income
    @Query("DELETE FROM expense_income_table where typeId = 2")
    void deleteAllIncome();

    @Query("SELECT * FROM expense_income_table where typeId = 2")
    LiveData<List<ExpenseIncome>> getAllIncome();

    @Query("SELECT * FROM expense_income_table WHERE date=:date and typeId = 2")
    LiveData<List<ExpenseIncome>> getDateIncome(String date);

    @Query("SELECT SUM(amount) FROM expense_income_table WHERE date=:date and typeId = 2")
    LiveData<Integer> getSumDateIncome(String date);

    @Query("Select SUM(amount) FROM expense_income_table where date  LIKE :date and typeId = 2")
    LiveData<Integer> getSumMonthIncome(String date);

    //Category
    @Insert
    void insertCategory(Category category);
    @Update
    void updateCategory(Category category);
    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM category_table")
    LiveData<List<Category>> getAllCategory();

    @Query("SELECT * FROM category_table WHERE cid=:id")
    Category getCategoryById(int id);

    @Query("SELECT * FROM category_table")
    List<Category> getListCategory();

    @Query("SELECT SUM(amount) FROM expense_income_table WHERE categoryId =:id and date LIKE :date")
    Integer getMonthCategorySum(int id, String date);
}
