package com.example.budgetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.budgetapp.CategoryExpense.CategoryExpense;
import com.example.budgetapp.CategoryExpense.CategoryExpenseViewModel;
import com.example.budgetapp.categoryDatabase.Category;
import com.example.budgetapp.expenseDatabase.Expense;
import com.example.budgetapp.expenseDatabase.ExpenseViewModel;
import com.example.budgetapp.recyclerviewAdapter.ExpenseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ExpenseRecyclerViewActivity extends AppCompatActivity {
    private ExpenseViewModel expenseViewModel;
    public static final int RESULT_OK = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_recycler_view);
        Intent dateIntent = getIntent();
        String date = dateIntent.getStringExtra(CalendarActivity.DATE_VALUE);

        FloatingActionButton buttonAddExpense = findViewById(R.id.button_add_expense);
        buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseRecyclerViewActivity.this, AddExpenseActivity.class);
                intent.putExtra(CalendarActivity.DATE_VALUE, date);
                startActivityForResult(intent, AddExpenseActivity.ADD_REQUEST_CODE);
            }
        });

        androidx.recyclerview.widget.RecyclerView recyclerView = findViewById(R.id.expense_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ExpenseAdapter expenseAdapter = new ExpenseAdapter();
        recyclerView.setAdapter(expenseAdapter);

        expenseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ExpenseViewModel.class);
        expenseViewModel.setFilter(date);
        expenseViewModel.getDateExpense().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                expenseAdapter.submitList(expenses);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                expenseViewModel.delete(expenseAdapter.getExpenseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ExpenseRecyclerViewActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        expenseAdapter.setOnItemClickListener(new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Expense expense) {
                Intent intent = new Intent(ExpenseRecyclerViewActivity.this, AddExpenseActivity.class);
                intent.putExtra(AddExpenseActivity.EXTRA_ID, expense.getEid());
                intent.putExtra(AddExpenseActivity.EXTRA_AMOUNT, expense.getAmount());
                intent.putExtra(AddExpenseActivity.EXTRA_DATE, expense.getDate());
                intent.putExtra(AddExpenseActivity.EXTRA_TITLE, expense.getTitle());
                intent.putExtra(AddExpenseActivity.EXTRA_DESCRIPTION, expense.getDescription());
                intent.putExtra(AddExpenseActivity.EXTRA_CATEGORY, expense.getCategoryId());
                startActivityForResult(intent, AddExpenseActivity.UPDATE_REQUEST_CODE);
            }
        });
    }


    @Override
    public void onActivityResult(int request_code, int result_code, Intent data) {
        super.onActivityResult(request_code, result_code, data);
        Category category;
        if (request_code == AddExpenseActivity.ADD_REQUEST_CODE && result_code == RESULT_OK) {

            String title = data.getStringExtra(AddExpenseActivity.EXTRA_TITLE);
            String date = data.getStringExtra(AddExpenseActivity.EXTRA_DATE);
            int amount = data.getIntExtra(AddExpenseActivity.EXTRA_AMOUNT, 0);
            String description = data.getStringExtra(AddExpenseActivity.EXTRA_DESCRIPTION);
            Expense expense = new Expense(title, description, amount, date);
            int categoryId = data.getIntExtra(AddExpenseActivity.EXTRA_CATEGORY, 0);
            expense.setCategoryId(categoryId);
            expenseViewModel.insert(expense);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } else if (request_code == AddExpenseActivity.UPDATE_REQUEST_CODE && result_code == RESULT_OK) {
            int id = data.getIntExtra(AddExpenseActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddExpenseActivity.EXTRA_TITLE);
            String date = data.getStringExtra(AddExpenseActivity.EXTRA_DATE);
            String description = data.getStringExtra(AddExpenseActivity.EXTRA_DESCRIPTION);
            int amount = data.getIntExtra(AddExpenseActivity.EXTRA_AMOUNT, 1);
            int categoryId = data.getIntExtra(AddExpenseActivity.EXTRA_CATEGORY, 0);
            Expense expense = new Expense(title, description, amount, date);
            expense.setEid(id);
            expense.setCategoryId(categoryId);
            expenseViewModel.update(expense);
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Not saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                expenseViewModel.deleteAll();
                Toast.makeText(this, "All deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}