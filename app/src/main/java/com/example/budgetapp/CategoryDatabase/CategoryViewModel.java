package com.example.budgetapp.CategoryDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> allCategory;
    private MutableLiveData<Category> findCategoryById;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
        allCategory = categoryRepository.getAllCategory();
        findCategoryById = categoryRepository.getCategoryById();

    }
    public MutableLiveData<Category> getCategoryResult() { return findCategoryById; }
    public LiveData<List<Category>> getAllCategory(){ return allCategory; }
    public void findCategoryId(Integer id ){ categoryRepository.findCategory(id);}
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
