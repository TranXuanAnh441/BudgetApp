package com.example.budgetapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.budgetapp.Database.AppViewModel;
import com.example.budgetapp.Database.Category.Category;
import com.example.budgetapp.Fragments.CalendarFragment;
import com.example.budgetapp.R;
import com.example.budgetapp.Adapters.CategoryRCVAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class CategoryRecyclerViewActivity extends AppCompatActivity {
    private AppViewModel categoryViewModel;
    public static final String ADD_CATEGORY = "com.example.budgetapp.ADD_CATEGORY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_recycler_view);

        RecyclerView recyclerView = findViewById(R.id.category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CategoryRCVAdapter categoryRCVAdapter = new CategoryRCVAdapter();
        recyclerView.setAdapter(categoryRCVAdapter);

        categoryViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AppViewModel.class);
        categoryViewModel.getAllCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryRCVAdapter.setCategories(categories);
            }
        });

        categoryRCVAdapter.setOnItemClickListener(new CategoryRCVAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Category category) {
                Intent categoryIntent = new Intent(CategoryRecyclerViewActivity.this, AddExpenseIncomeActivity.class);
                categoryIntent.putExtra(ADD_CATEGORY, (Serializable) category);
                setResult(ExpenseIncomeRCVActivity.RESULT_OK, categoryIntent);
                finish();
            }

        });
    }}
