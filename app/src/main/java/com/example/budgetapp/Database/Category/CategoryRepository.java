package com.example.budgetapp.Database.Category;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.budgetapp.AppDatabase;
import com.example.budgetapp.Database.AppDao;

import java.util.List;


public class CategoryRepository {
    private AppDao appDao;
    private LiveData<List<Category>> allCategory;
    private MutableLiveData<Category> categoryMutableLiveData = new MutableLiveData<>();

    private List<Category> listCategory ;

    public CategoryRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        appDao = appDatabase.appDao();
        allCategory = appDao.getAllCategory();
    }
    private void AsyncFinished(Category category){
        categoryMutableLiveData.setValue(category);
    }


    public MutableLiveData<Category> getCategoryById() { return categoryMutableLiveData; }
    public LiveData<List<Category>> getAllCategory(){ return allCategory; }

    public void insert(Category category) {
        new com.example.budgetapp.Database.Category.CategoryRepository.InsertCategoryAsyncTask(appDao).execute(category);
    }

    public void update(Category category) {
        new com.example.budgetapp.Database.Category.CategoryRepository.UpdateCategoryAsyncTask(appDao).execute(category);
    }

    public void delete(Category category) {
        new com.example.budgetapp.Database.Category.CategoryRepository.DeleteCategoryAsyncTask(appDao).execute(category);
    }
    public void findCategory(Integer id) {
        com.example.budgetapp.Database.Category.CategoryRepository.findCategoryAsyncTask task = new com.example.budgetapp.Database.Category.CategoryRepository.findCategoryAsyncTask(appDao);
        task.delegate = this;
        task.execute(id);
    }

    private static class findCategoryAsyncTask extends AsyncTask<Integer, Void, Category>{
        private AppDao appDao;
        private CategoryRepository delegate = null;

        public findCategoryAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Category doInBackground(Integer... integers) {
            return appDao.getCategoryById(integers[0]);
        }

        @Override
        protected void onPostExecute(Category category) {
            delegate.AsyncFinished(category);
        }
    }


    private static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void>{
        private AppDao appDao;

        public InsertCategoryAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            appDao.insertCategory(categories[0]);
            return null;
        }
    }

    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void>{
        private AppDao appDao;

        public UpdateCategoryAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            appDao.updateCategory(categories[0]);
            return null;
        }
    }

    private static class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void>{
        private AppDao appDao;

        public DeleteCategoryAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }
        @Override
        protected Void doInBackground(Category... categories) {
            appDao.deleteCategory(categories[0]);
            return null;
        }
    }
}
