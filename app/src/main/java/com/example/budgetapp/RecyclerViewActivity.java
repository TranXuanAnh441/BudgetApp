package com.example.budgetapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.budgetapp.database.Expense;
import com.example.budgetapp.recyclerview.ExpenseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private ExpenseViewModel expenseViewModel;
    public static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        FloatingActionButton buttonAddExpense = findViewById(R.id.button_add_expense);
        buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerViewActivity.this, addExpenseActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        androidx.recyclerview.widget.RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ExpenseAdapter expenseAdapter = new ExpenseAdapter();
        recyclerView.setAdapter(expenseAdapter);

        expenseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpense().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                expenseAdapter.setExpenses(expenses);
            }
        });

    }
    @Override
    public void onActivityResult(int request_code, int result_code, Intent data) {
        super.onActivityResult(request_code, result_code, data);
        if(request_code == REQUEST_CODE && result_code == 100){
            String title = data.getStringExtra(addExpenseActivity.EXTRA_TITLE);
            String date = data.getStringExtra(addExpenseActivity.EXTRA_DATE);
            int amount = data.getIntExtra(addExpenseActivity.EXTRA_AMOUNT,0);
            String description = data.getStringExtra(addExpenseActivity.EXTRA_DESCRIPTION);
            Expense expense = new Expense(title,description,amount,date);
            expenseViewModel.insert(expense);
            Toast.makeText(this, " saved", Toast.LENGTH_SHORT).show();
        }
    }
}