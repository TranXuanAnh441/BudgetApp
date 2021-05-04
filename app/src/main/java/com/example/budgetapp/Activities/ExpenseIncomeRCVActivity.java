package com.example.budgetapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.budgetapp.Database.AppViewModel;
import com.example.budgetapp.Database.ExpenseIncome.ExpenseIncome;
import com.example.budgetapp.Fragments.CalendarFragment;
import com.example.budgetapp.Fragments.ExpenseRecyclerViewFragment;
import com.example.budgetapp.Fragments.IncomeRecyclerViewFragment;
import com.example.budgetapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExpenseIncomeRCVActivity extends AppCompatActivity {
    private AppViewModel appViewModel;

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
        date = dateIntent.getStringExtra(CalendarFragment.DATE_VALUE);
        appViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AppViewModel.class);

        FloatingActionButton buttonAddExpense = findViewById(R.id.button_add_category1);
        buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dateIntent = getIntent();
                String date = dateIntent.getStringExtra(CalendarFragment.DATE_VALUE);
                Intent intent = new Intent(ExpenseIncomeRCVActivity.this, AddExpenseIncomeActivity.class);
                intent.putExtra(CalendarFragment.DATE_VALUE, date);
                startActivityForResult(intent, AddExpenseIncomeActivity.ADD_REQUEST_CODE);
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
        if (request_code == AddExpenseIncomeActivity.ADD_REQUEST_CODE && result_code == AddExpenseIncomeActivity.RESULT_OK) {
            String title = data.getStringExtra(AddExpenseIncomeActivity.EXTRA_TITLE);
            String date = data.getStringExtra(AddExpenseIncomeActivity.EXTRA_DATE);
            int amount = data.getIntExtra(AddExpenseIncomeActivity.EXTRA_AMOUNT, 0);
            String description = data.getStringExtra(AddExpenseIncomeActivity.EXTRA_DESCRIPTION);
            int typeId = data.getIntExtra(AddExpenseIncomeActivity.EXTRA_TYPE_ID,0);
            ExpenseIncome expenseIncome = new ExpenseIncome(title, description, amount, date, typeId);
            int categoryId = data.getIntExtra(AddExpenseIncomeActivity.EXTRA_CATEGORY, 0);
            expenseIncome.setCategoryId(categoryId);
            appViewModel.insertExpenseIncome(expenseIncome);
            Toast.makeText(this, "saved", Toast.LENGTH_LONG).show();
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
                appViewModel.deleteAllExpense();
                Toast.makeText(this, "All deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}