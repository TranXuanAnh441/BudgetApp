package com.example.budgetapp.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import com.example.budgetapp.Database.AppViewModel;
import com.example.budgetapp.Database.Category.Category;
import com.example.budgetapp.Database.ExpenseIncome.ExpenseIncome;
import com.example.budgetapp.R;
import com.example.budgetapp.Adapters.CategoryRCVAdapter;
import com.example.budgetapp.Adapters.ExpenseRCVAdapter;
import com.example.budgetapp.Adapters.IncomeRCVAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.View;
import android.widget.SearchView;


import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private IncomeRCVAdapter incomeRCVAdapter;
    private AppViewModel appViewModel;
    private ExpenseRCVAdapter expenseRCVAdapter;

    private CategoryRCVAdapter categoryRCVAdapter;

    private ConcatAdapter concatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        expenseRCVAdapter =  new ExpenseRCVAdapter();
        recyclerView = findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        recyclerView.setHasFixedSize(true);

        categoryRCVAdapter = new CategoryRCVAdapter();
        incomeRCVAdapter = new IncomeRCVAdapter();
        concatAdapter = new ConcatAdapter(expenseRCVAdapter, incomeRCVAdapter, categoryRCVAdapter);
        recyclerView.setAdapter(concatAdapter);
        recyclerView.setVisibility(View.GONE);

        appViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AppViewModel.class);
        appViewModel.getAllCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryRCVAdapter.setCategories(categories);
            }
        });
        appViewModel.getAllExpense().observe(this, new Observer<List<ExpenseIncome>>() {
            @Override
            public void onChanged(List<ExpenseIncome> expenses) {
                expenseRCVAdapter.setExpenses(expenses);
            }
        });
        appViewModel.getAllIncome().observe(this, new Observer<List<ExpenseIncome>>() {
            @Override
            public void onChanged(List<ExpenseIncome> incomes) {
                incomeRCVAdapter.setIncomes(incomes);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                categoryRCVAdapter.getFilter().filter(query);
                expenseRCVAdapter.getFilter().filter(query);
                incomeRCVAdapter.getFilter().filter(query);
                recyclerView.setVisibility(View.VISIBLE);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                categoryRCVAdapter.getFilter().filter(newText);
                expenseRCVAdapter.getFilter().filter(newText);
                incomeRCVAdapter.getFilter().filter(newText);
                recyclerView.setVisibility(View.VISIBLE);
                return false;
            }
        });
        return true;
    }
}