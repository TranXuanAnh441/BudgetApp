package com.example.budgetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetapp.Fragments.CalendarFragment;
import com.example.budgetapp.CategoryDatabase.Category;
import com.example.budgetapp.CategoryDatabase.CategoryViewModel;


public class AddExpenseActivity extends AppCompatActivity {
    public static final int INCOME_RESULT = 101;
    public static final int EXPENSE_RESULT = 100;
    private EditText addTitle;
    private EditText addAmount;
    private EditText addDescription;
    private TextView addCategory;
    private TextView addDate;
    private Category thisCategory;
    private RadioButton radio_income, radio_expense;
    private CategoryViewModel categoryViewModel;
    private String date;
    public static final int ADD_REQUEST_CODE = 1;
    public static final int UPDATE_REQUEST_CODE = 2;
    public static final String EXPENSE_ID = "com.example.budgetapp.EXPENSE_ID";
    public static final String EXPENSE_TITLE = "com.example.budgetapp.EXPENSE_TITLE";
    public static final String EXPENSE_AMOUNT = "com.example.budgetapp.EXPENSE_AMOUNT";
    public static final String EXPENSE_DATE = "com.example.budgetapp.EXPENSE_DATE";
    public static final String EXPENSE_DESCRIPTION = "com.example.budgetapp.EXPENSE_DESCRIPTION";
    public static final String EXTRA_CATEGORY = "com.example.budgetapp.EXTRA_CATEGORY";
    public static final String INCOME_ID = "com.example.budgetapp.INCOME_ID";
    public static final String INCOME_TITLE = "com.example.budgetapp.INCOME_TITLE";
    public static final String INCOME_AMOUNT = "com.example.budgetapp.INCOME_AMOUNT";
    public static final String INCOME_DATE = "com.example.budgetapp.INCOME_DATE";
    public static final String INCOME_DESCRIPTION = "com.example.budgetapp.INCOME_DESCRIPTION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Button addCategoryBtn = findViewById(R.id.addCategoryBtn);
        addCategoryBtn.setClickable(true);
        categoryViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CategoryViewModel.class); ;
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(AddExpenseActivity.this, CategoryRecyclerViewActivity.class);
                startActivityForResult(categoryIntent, ADD_REQUEST_CODE);
            }
        });
        categoryViewModel.getCategoryResult().observe(this, new Observer<Category>() {
            @Override
            public void onChanged(Category category) {
                if(category != null){
                addCategory.setText(category.getName());}
            }
        });
        addAmount = findViewById(R.id.edit_text_amount);
        addTitle = findViewById(R.id.edit_text_title);
        addDescription = findViewById(R.id.edit_text_description);
        addCategory = findViewById(R.id.categoryTextView);
        addDate = findViewById(R.id.dateTextView);
        radio_income = findViewById(R.id.radio_income);
        radio_expense =findViewById(R.id.radio_expense);
        radio_income.setOnCheckedChangeListener(listenerRadio);
        radio_expense.setOnCheckedChangeListener(listenerRadio);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close);
        Intent intent = getIntent();
        date = intent.getStringExtra(CalendarFragment.DATE_VALUE);
        addDate.setText(date);
        if (intent.hasExtra(EXPENSE_ID)) {
            setTitle("Edit expense");
            addTitle.setText(intent.getStringExtra(EXPENSE_TITLE));
            addDescription.setText(intent.getStringExtra(EXPENSE_DESCRIPTION));
            addAmount.setText(String.valueOf(intent.getIntExtra(EXPENSE_AMOUNT, 0)));
            categoryViewModel.findCategoryId(intent.getIntExtra(EXTRA_CATEGORY, 0));
            addDate.setText(intent.getStringExtra(EXPENSE_DATE));
            ((RadioButton)findViewById(R.id.radio_income)).setChecked(false);
            ((RadioButton)findViewById(R.id.radio_expense)).setChecked(true);
            ((RadioButton)findViewById(R.id.radio_income)).setEnabled(false);
        }
        else if (intent.hasExtra(INCOME_ID)) {
            setTitle("Edit income");
            addTitle.setText(intent.getStringExtra(INCOME_TITLE));
            addDescription.setText(intent.getStringExtra(INCOME_DESCRIPTION));
            addAmount.setText(String.valueOf(intent.getIntExtra(INCOME_AMOUNT, 0)));
            addDate.setText(intent.getStringExtra(INCOME_DATE));
            ((RadioButton)findViewById(R.id.radio_income)).setChecked(true);
            ((RadioButton)findViewById(R.id.radio_expense)).setChecked(false);
            ((RadioButton)findViewById(R.id.radio_expense)).setEnabled(false);

        }
        else { setTitle("Add");}


    }

    CompoundButton.OnCheckedChangeListener listenerRadio
            = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(radio_income.isChecked()){
                Button btn = findViewById(R.id.addCategoryBtn);
                btn.setClickable(false);
            }
            else if(radio_expense.isChecked()){
                Button btn = findViewById(R.id.addCategoryBtn);
                btn.setClickable(true);
            }
        }
    };

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
                if(radio_expense.isChecked()) saveExpense();
                else if(radio_income.isChecked()) saveIncome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }}

    private void saveIncome() {
        String title = addTitle.getText().toString();
        String description = addDescription.getText().toString();
        String date = addDate.getText().toString();
        int amount = Integer.parseInt(addAmount.getText().toString());
        if (title.trim().isEmpty() || String.valueOf(amount).trim().isEmpty()) {
            Toast.makeText(this, "Please insert properly", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent(this, ExpenseIncomeRCVActivity.class);
        data.putExtra(INCOME_TITLE, title);
        data.putExtra(INCOME_DESCRIPTION, description);
        data.putExtra(INCOME_DATE, date);
        data.putExtra(INCOME_AMOUNT, amount);
        int id = getIntent().getIntExtra(INCOME_ID, -1);
        if (id != -1) {
            data.putExtra(INCOME_ID, id);
        }
        setResult(INCOME_RESULT, data);
        finish();
    }


    private void saveExpense() {
        String title = addTitle.getText().toString();
        String description = addDescription.getText().toString();
        String date = addDate.getText().toString();
        int amount = Integer.parseInt(addAmount.getText().toString());
        if (title.trim().isEmpty() || String.valueOf(amount).trim().isEmpty()) {
            Toast.makeText(this, "Please insert properly", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent(this, ExpenseIncomeRCVActivity.class);
        data.putExtra(EXPENSE_TITLE, title);
        data.putExtra(EXPENSE_DESCRIPTION, description);
        data.putExtra(EXPENSE_AMOUNT, amount);
        data.putExtra(EXPENSE_DATE, date);
        if(thisCategory != null){
        data.putExtra(EXTRA_CATEGORY, thisCategory.getCid());}
        int id = getIntent().getIntExtra(EXPENSE_ID, -1);
        if (id != -1) {
            data.putExtra(EXPENSE_ID, id);
        }
        setResult(EXPENSE_RESULT, data);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_REQUEST_CODE && resultCode == ExpenseIncomeRCVActivity.RESULT_OK){
            TextView categoryTextView = findViewById(R.id.categoryTextView);
            thisCategory = (Category) data.getExtras().getSerializable(CategoryRecyclerViewActivity.ADD_CATEGORY);
            categoryTextView.setText(thisCategory.getName());
            }
        }
    }
