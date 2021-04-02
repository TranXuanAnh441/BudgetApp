package com.example.budgetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetapp.categoryDatabase.Category;

import java.io.Serializable;

public class AddExpenseActivity extends AppCompatActivity {
    private EditText addTitle;
    private EditText addAmount;
    private EditText addDescription;
    private TextView addDate;
    private Category category;
    public static final int ADD_REQUEST_CODE = 1;
    public static final int UPDATE_REQUEST_CODE = 2;
    public static final String EXTRA_ID = "com.example.budgetapp.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.budgetapp.EXTRA_TITLE";
    public static final String EXTRA_AMOUNT = "com.example.budgetapp.EXTRA_AMOUNT";
    public static final String EXTRA_DATE = "com.example.budgetapp.EXTRA_DATE";
    public static final String EXTRA_DESCRIPTION = "com.example.budgetapp.EXTRA_DESCRIPTION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Button addCategoryBtn = findViewById(R.id.addCategoryBtn);

        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(AddExpenseActivity.this, CategoryRecyclerViewActivity.class);
                startActivityForResult(categoryIntent, ADD_REQUEST_CODE);
            }
        });

        addAmount = findViewById(R.id.edit_text_amount);
        addTitle = findViewById(R.id.edit_text_title);
        addDescription = findViewById(R.id.edit_text_description);
        addDate = findViewById(R.id.edit_text_date);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            addTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            addDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            addAmount.setText(String.valueOf(intent.getIntExtra(EXTRA_AMOUNT, 0)));
            addDate.setText(intent.getStringExtra(EXTRA_DATE));
        } else { addDate.setText(intent.getStringExtra(CalendarActivity.DATE_VALUE)); setTitle("Add Expense");}
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
        TextView categoryTextView = findViewById(R.id.categoryTextView);
        String date = addDate.getText().toString();
        String title = addTitle.getText().toString();
        String description = addDescription.getText().toString();
        String categoryName = categoryTextView.getText().toString();
        int amount = Integer.parseInt(addAmount.getText().toString());
        if (title.trim().isEmpty() || String.valueOf(amount).trim().isEmpty() || date.trim().isEmpty()) {
            Toast.makeText(this, "Please insert properly", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent(this, ExpenseRecyclerViewActivity.class);
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_AMOUNT, amount);
        data.putExtra(CategoryRecyclerViewActivity.ADD_CATEGORY, (Serializable) category);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(ExpenseRecyclerViewActivity.RESULT_OK, data);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_REQUEST_CODE && resultCode == ExpenseRecyclerViewActivity.RESULT_OK){
            TextView categoryTextView = findViewById(R.id.categoryTextView);
            category = (Category) data.getExtras().getSerializable(CategoryRecyclerViewActivity.ADD_CATEGORY);
            categoryTextView.setText(category.getName());
            }
        }
    }