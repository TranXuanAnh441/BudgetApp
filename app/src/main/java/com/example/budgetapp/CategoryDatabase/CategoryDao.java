package com.example.budgetapp.CategoryDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


@Dao
public interface CategoryDao {
    @Insert
    void insert(Category category);
    @Update
    void update(Category category);
    @Delete
    void delete(Category category);
    @Query("SELECT * FROM category_table")
    LiveData<List<Category>> getAllCategory();
    @Query("SELECT * FROM category_table WHERE cid=:id")
    Category getCategoryById(int id);
    @Query("SELECT * FROM category_table")
    List<Category> getListCategory();
    @Query("SELECT SUM(amount) FROM expense_table WHERE categoryId =:id and date LIKE :date")
    Integer getMonthlyCategorySum(int id, String date);
}
