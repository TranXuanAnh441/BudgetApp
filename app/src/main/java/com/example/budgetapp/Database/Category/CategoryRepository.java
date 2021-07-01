package com.example.budgetapp.Database.Category;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.example.budgetapp.AppDatabase;
import com.example.budgetapp.Database.AppDao;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CategoryRepository {
    private AppDao appDao;
    private LiveData<List<Category>> allCategory;
    private MutableLiveData<Category> categoryMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<DataEntry>> dataEntriesMutableLiveData = new MutableLiveData<>();

    private List<Category> listCategory ;

    public CategoryRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        appDao = appDatabase.appDao();
        allCategory = appDao.getAllCategory();
    }
    private void AsyncFinished(Category category){
        categoryMutableLiveData.setValue(category);
    }

    private void MonthPieAsync(List<DataEntry> dataEntries){
        dataEntriesMutableLiveData.setValue(dataEntries);
    }


    public MutableLiveData<Category> getCategoryById() { return categoryMutableLiveData; }
    public MutableLiveData<List<DataEntry>> getMonthPie(){return dataEntriesMutableLiveData; }
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

    public void getMonthPie(String date){
        CategoryRepository.MonthSumCalculateAsyncTask task = new CategoryRepository.MonthSumCalculateAsyncTask(appDao);
        task.delegate = this;
        task.execute(date);
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

    private static class MonthSumCalculateAsyncTask extends AsyncTask<String,Void, List<DataEntry>>{
        private AppDao appDao;
        private CategoryRepository delegate = null;
        public MonthSumCalculateAsyncTask(AppDao appDao){this.appDao = appDao;}
        @Override
        protected List<DataEntry> doInBackground(String... strings) {
            List<Integer> categoryIds = appDao.getMonthCategory(strings[0]);
            List<Integer> sum = new ArrayList<>();
            List<String> categoryNames = new ArrayList<>();
            for(Integer ids : categoryIds){
                sum.add(appDao.getMonthCategorySum(ids));
                categoryNames.add(appDao.getCategoryNames(ids));
            }
            List<DataEntry> dataEntries = new ArrayList<>();
            for(int i = 0; i < categoryNames.size(); i ++){
                dataEntries.add(new ValueDataEntry(categoryNames.get(i), sum.get(i)));
            }
            return dataEntries;
        }

        @Override
        protected void onPostExecute(List<DataEntry> dataEntries) {
            delegate.MonthPieAsync(dataEntries);
            super.onPostExecute(dataEntries);
        }
    }
}
