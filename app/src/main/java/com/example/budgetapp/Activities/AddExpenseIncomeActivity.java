package com.example.budgetapp.Activities;

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

import com.example.budgetapp.Database.AppViewModel;
import com.example.budgetapp.Database.Category.Category;
import com.example.budgetapp.Fragments.CalendarFragment;
import com.example.budgetapp.R;


public class AddExpenseIncomeActivity extends AppCompatActivity {
    public static final int RESULT_OK = 100;
    private EditText addTitle;
    private EditText addAmount;
    private EditText addDescription;
    private TextView addCategory;
    private TextView addDate;
    private Category thisCategory;
    private RadioButton radio_income, radio_expense;
    private AppViewModel appViewModel;
    private String date;
    private int typeId;
    public static final int ADD_REQUEST_CODE = 1;
    public static final int UPDATE_REQUEST_CODE = 2;
    public static final int EXPENSE_TYPE_ID = 1;
    public static final int INCOME_TYPE_ID = 2;
    public static final String EXTRA_CATEGORY = "com.example.budgetapp.CATEGORY";
    public static final String EXTRA_ID = "com.example.budgetapp.ID";
    public static final String EXTRA_TYPE_ID = "com.example.budgetapp.TYPE_ID";
    public static final String EXTRA_TITLE = "com.example.budgetapp.TITLE";
    public static final String EXTRA_AMOUNT = "com.example.budgetapp.AMOUNT";
    public static final String EXTRA_DATE = "com.example.budgetapp.DATE";
    public static final String EXTRA_DESCRIPTION = "com.example.budgetapp.DESCRIPTION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Button addCategoryBtn = findViewById(R.id.addCategoryBtn);
        addCategoryBtn.setClickable(true);
        appViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AppViewModel.class); ;
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(AddExpenseIncomeActivity.this, CategoryRecyclerViewActivity.class);
                startActivityForResult(categoryIntent, ADD_REQUEST_CODE);
            }
        });
        appViewModel.getCategoryResult().observe(this, new Observer<Category>() {
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

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close);
        Intent intent = getIntent();
        date = intent.getStringExtra(CalendarFragment.DATE_VALUE);
        addDate.setText(date);
        typeId = intent.getIntExtra(EXTRA_TYPE_ID, 0);
        if(intent.getLongExtra(EXTRA_ID, -1) != -1) {
            if (typeId == EXPENSE_TYPE_ID) {
                setTitle("Update expense");
                ((RadioButton) findViewById(R.id.radio_income)).setChecked(false);
                ((RadioButton) findViewById(R.id.radio_expense)).setChecked(true);
            }
            if (typeId == INCOME_TYPE_ID) {
                setTitle("Update income");
                ((RadioButton) findViewById(R.id.radio_income)).setChecked(true);
                ((RadioButton) findViewById(R.id.radio_expense)).setChecked(false);
            }
            addTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            addDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            addAmount.setText(String.valueOf(intent.getIntExtra(EXTRA_AMOUNT, 0)));
            appViewModel.findCategoryId(intent.getIntExtra(EXTRA_CATEGORY, 0));
            addDate.setText(intent.getStringExtra(EXTRA_DATE));
        }
        else { setTitle("Add");}
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
                if(radio_expense.isChecked()) saveExpenseIncome(EXPENSE_TYPE_ID);
                else if(radio_income.isChecked()) saveExpenseIncome(INCOME_TYPE_ID);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }}

    private void saveExpenseIncome(int type){
        String title = addTitle.getText().toString();
        String description = addDescription.getText().toString();
        String date = addDate.getText().toString();
        int amount = Integer.parseInt(addAmount.getText().toString());
        int typeId = type;
        if (title.trim().isEmpty() || description.trim().isEmpty() || String.valueOf(amount).trim().isEmpty()) {
            Toast.makeText(this, "Please insert properly", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent(this, ExpenseIncomeRCVActivity.class);
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_AMOUNT, amount);
        data.putExtra(EXTRA_TYPE_ID, typeId);
        if(thisCategory != null){
            data.putExtra(EXTRA_CATEGORY, thisCategory.getCid());}
        long id = getIntent().getLongExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
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
