package com.example.budgetapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.budgetapp.CategoryDatabase.Category;
import com.example.budgetapp.CategoryDatabase.CategoryViewModel;
import com.example.budgetapp.recyclerviewAdapter.CategoryAdapter;

import java.io.Serializable;
import java.util.List;

public class CategoryRecyclerViewActivity extends AppCompatActivity {
    private CategoryViewModel categoryViewModel;
    private Context context;
    public static final String ADD_CATEGORY = "com.example.budgetapp.ADD_CATEGORY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_recycler_view);

        RecyclerView recyclerView = findViewById(R.id.category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CategoryAdapter categoryAdapter = new CategoryAdapter();
        recyclerView.setAdapter(categoryAdapter);

        categoryViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CategoryViewModel.class);
        categoryViewModel.getAllCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryAdapter.setCategories(categories);
            }
        });

        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Category category) {
                Intent categoryIntent = new Intent(CategoryRecyclerViewActivity.this, AddExpenseActivity.class);
                categoryIntent.putExtra(ADD_CATEGORY, (Serializable) category);
                setResult(ExpenseIncomeRCVActivity.RESULT_OK, categoryIntent);
                finish();
            }
        });
    }
}