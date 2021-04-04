package com.example.budgetapp.categoryDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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
}
