package com.example.budgetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.budgetapp.Fragments.ExpenseFragment;
import com.example.budgetapp.Fragments.ExpenseRecyclerViewFragment;
import com.example.budgetapp.Fragments.IncomeRecyclerViewFragment;
import com.example.budgetapp.expenseDatabase.Expense;
import com.example.budgetapp.expenseDatabase.ExpenseViewModel;
import com.example.budgetapp.incomeDatabase.Income;
import com.example.budgetapp.incomeDatabase.IncomeViewModel;
import com.example.budgetapp.recyclerviewAdapter.ExpenseAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExpenseIncomeRCVActivity extends AppCompatActivity {
    private ExpenseViewModel expenseViewModel;
    private IncomeViewModel incomeViewModel;

    public String getDate() {
        return date;
    }

    private String date;
    public static final int RESULT_OK = 100;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_recycler_view);
        Intent dateIntent = getIntent();
        date = dateIntent.getStringExtra(ExpenseFragment.DATE_VALUE);
        expenseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ExpenseViewModel.class);
        incomeViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(IncomeViewModel.class);
        FloatingActionButton buttonAddExpense = findViewById(R.id.button_add_category1);
        buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dateIntent = getIntent();
                String date = dateIntent.getStringExtra(ExpenseFragment.DATE_VALUE);
                Intent intent = new Intent(ExpenseIncomeRCVActivity.this, AddExpenseActivity.class);
                intent.putExtra(ExpenseFragment.DATE_VALUE, date);
                startActivityForResult(intent, AddExpenseActivity.ADD_REQUEST_CODE);
            }
        });

        if (savedInstanceState == null){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ExpenseRecyclerViewFragment()).commit();}

        bottomNavigationView = findViewById(R.id.top_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.expense_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ExpenseRecyclerViewFragment()).commit();
                        break;
                    case R.id.income_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new IncomeRecyclerViewFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public void onActivityResult(int request_code, int result_code, Intent data) {
        super.onActivityResult(request_code, result_code, data);
        if (request_code == AddExpenseActivity.ADD_REQUEST_CODE && result_code == AddExpenseActivity.EXPENSE_RESULT) {
            String title = data.getStringExtra(AddExpenseActivity.EXPENSE_TITLE);
            String date = data.getStringExtra(AddExpenseActivity.EXPENSE_DATE);
            int amount = data.getIntExtra(AddExpenseActivity.EXPENSE_AMOUNT, 0);
            String description = data.getStringExtra(AddExpenseActivity.EXPENSE_DESCRIPTION);
            Expense expense = new Expense(title, description, amount, date);
            int categoryId = data.getIntExtra(AddExpenseActivity.EXTRA_CATEGORY, 0);
            expense.setCategoryId(categoryId);
            expenseViewModel.insert(expense);
            Toast.makeText(this, "saved", Toast.LENGTH_LONG).show();
        }
        else if (request_code == AddExpenseActivity.ADD_REQUEST_CODE && result_code == AddExpenseActivity.INCOME_RESULT){
            String title = data.getStringExtra(AddExpenseActivity.INCOME_TITLE);
            String date = data.getStringExtra(AddExpenseActivity.INCOME_DATE);
            int amount = data.getIntExtra(AddExpenseActivity.INCOME_AMOUNT, 0);
            String description = data.getStringExtra(AddExpenseActivity.INCOME_DESCRIPTION);
            Income income = new Income(title, description, amount, date);
            incomeViewModel.insert(income);
        }
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