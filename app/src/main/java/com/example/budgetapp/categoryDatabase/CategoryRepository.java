package com.example.budgetapp.categoryDatabase;
import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.budgetapp.categoryDatabase.Category;
import com.example.budgetapp.categoryDatabase.CategoryDao;
import com.example.budgetapp.categoryDatabase.CategoryDatabase;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategory;

    public CategoryRepository(Application application) {
        CategoryDatabase categoryDatabase = CategoryDatabase.getInstance(application);
        categoryDao = categoryDatabase.categoryDao();
        allCategory = categoryDao.getAllCategory();
    }
    public LiveData<List<Category>> getAllCategory(){ return allCategory; }

    public void insert(Category category) {
        new InsertCategoryAsyncTask(categoryDao).execute(category);
    }

    public void update(Category category) {
        new UpdateCategoryAsyncTask(categoryDao).execute(category);
    }

    public void delete(Category category) {
        new DeleteCategoryAsyncTask(categoryDao).execute(category);
    }

    private static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void>{
        private CategoryDao categoryDao;

        public InsertCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.insert(categories[0]);
            return null;
        }
    }

    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void>{
        private CategoryDao categoryDao;

        public UpdateCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.update(categories[0]);
            return null;
        }
    }

    private static class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void>{
        private CategoryDao categoryDao;

        public DeleteCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }
        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.delete(categories[0]);
            return null;
        }
    }
}
