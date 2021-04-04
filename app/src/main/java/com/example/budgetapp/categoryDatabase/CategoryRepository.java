package com.example.budgetapp.categoryDatabase;
import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.budgetapp.AppDatabase;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategory;
    private MutableLiveData<Category> categoryMutableLiveData = new MutableLiveData<>();

    public CategoryRepository(Application application) {
        AppDatabase categoryDatabase = AppDatabase.getInstance(application);
        categoryDao = categoryDatabase.categoryDao();
        allCategory = categoryDao.getAllCategory();
    }
    private void AsyncFinished(Category category){
        categoryMutableLiveData.setValue(category);
    }

    public MutableLiveData<Category> getCategoryById() { return categoryMutableLiveData; }
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
    public void findCategory(Integer id) {
        findCategoryAsyncTask task = new findCategoryAsyncTask(categoryDao);
        task.delegate = this;
        task.execute(id);
    }

    private static class findCategoryAsyncTask extends AsyncTask<Integer, Void, Category>{
        private CategoryDao categoryDao;
        private CategoryRepository delegate = null;

        public findCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Category doInBackground(Integer... integers) {
            return categoryDao.getCategoryById(integers[0]);
        }

        @Override
        protected void onPostExecute(Category category) {
            delegate.AsyncFinished(category);
        }
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
