package com.example.budgetapp.categoryDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.budgetapp.categoryDatabase.Category;
import com.example.budgetapp.categoryDatabase.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> allCategory;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
        allCategory = categoryRepository.getAllCategory();
    }
    public LiveData<List<Category>> getAllCategory(){ return allCategory; }
    public void insert(Category category){
        categoryRepository.insert(category);
    }
    public void update(Category category){
        categoryRepository.update(category);
    }
    public void delete(Category category){
        categoryRepository.delete(category);
    }
}
