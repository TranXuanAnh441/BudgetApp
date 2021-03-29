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

import com.example.budgetapp.database.Expense;
import com.example.budgetapp.recyclerview.ExpenseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private ExpenseViewModel expenseViewModel;
    public static final int RESULT_OK = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        Intent dateIntent = getIntent();
        String date = dateIntent.getStringExtra(CalendarActivity.DATE_VALUE);

        FloatingActionButton buttonAddExpense = findViewById(R.id.button_add_expense);
        buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerViewActivity.this, addExpenseActivity.class);
                intent.putExtra(CalendarActivity.DATE_VALUE, date);
                startActivityForResult(intent, addExpenseActivity.ADD_REQUEST_CODE);
            }
        });
        androidx.recyclerview.widget.RecyclerView recyclerView = findViewById(R.id.recycler_view);
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
                Toast.makeText(RecyclerViewActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        expenseAdapter.setOnItemClickListener(new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Expense expense) {
                Intent intent = new Intent(RecyclerViewActivity.this, addExpenseActivity.class);
                intent.putExtra(addExpenseActivity.EXTRA_ID, expense.getId());
                intent.putExtra(addExpenseActivity.EXTRA_AMOUNT, expense.getAmount());
                intent.putExtra(addExpenseActivity.EXTRA_DATE, expense.getDate());
                intent.putExtra(addExpenseActivity.EXTRA_TITLE, expense.getTitle());
                intent.putExtra(addExpenseActivity.EXTRA_DESCRIPTION, expense.getDescription());
                startActivityForResult(intent, addExpenseActivity.UPDATE_REQUEST_CODE);
            }
        });
    }


    @Override
    public void onActivityResult(int request_code, int result_code, Intent data) {
        super.onActivityResult(request_code, result_code, data);
        if (request_code == addExpenseActivity.ADD_REQUEST_CODE && result_code == RESULT_OK) {
            String title = data.getStringExtra(addExpenseActivity.EXTRA_TITLE);
            String date = data.getStringExtra(addExpenseActivity.EXTRA_DATE);
            int amount = data.getIntExtra(addExpenseActivity.EXTRA_AMOUNT, 0);
            String description = data.getStringExtra(addExpenseActivity.EXTRA_DESCRIPTION);
            Expense expense = new Expense(title, description, amount, date);
            expenseViewModel.insert(expense);
            Toast.makeText(this, " saved", Toast.LENGTH_SHORT).show();
        } else if (request_code == addExpenseActivity.UPDATE_REQUEST_CODE && result_code == RESULT_OK) {
            int id = data.getIntExtra(addExpenseActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(addExpenseActivity.EXTRA_TITLE);
            String date = data.getStringExtra(addExpenseActivity.EXTRA_DATE);
            String description = data.getStringExtra(addExpenseActivity.EXTRA_DESCRIPTION);
            int amount = data.getIntExtra(addExpenseActivity.EXTRA_AMOUNT, 1);
            Expense expense = new Expense(title, description, amount, date);
            expense.setId(id);
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