package com.example.budgetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class addExpenseActivity extends AppCompatActivity {
    private EditText addTitle;
    private EditText addAmount;
    private EditText addDescription;
    private EditText addDate;
    public static final String EXTRA_TITLE = "com.example.budgetapp.EXTRA_TITLE";
    public static final String EXTRA_AMOUNT = "com.example.budgetapp.EXTRA_AMOUNT";
    public static final String EXTRA_DATE = "com.example.budgetapp.EXTRA_DATE";
    public static final String EXTRA_DESCRIPTION = "com.example.budgetapp.EXTRA_DESCRIPTION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        addAmount = findViewById(R.id.edit_text_amount);
        addTitle = findViewById(R.id.edit_text_title);
        addDescription = findViewById(R.id.edit_text_description);
        addDate = findViewById(R.id.edit_text_date);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close);
        setTitle("Add Expense");
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.add_expense_menu, menu);
            return true;
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_expense:
                saveExpense();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }}


    private void saveExpense() {
        String date = addDate.getText().toString();
        String title = addTitle.getText().toString();
        String description = addDescription.getText().toString();
        int amount = Integer.parseInt(addAmount.getText().toString());

        Intent data = new Intent(this,RecyclerViewActivity.class);
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_AMOUNT, amount);
        setResult(100,data);
        finish();
    }
}