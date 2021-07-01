package com.example.budgetapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.budgetapp.Fragments.CategoryFragment;
import com.example.budgetapp.R;

public class AddCategoryActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.example.budgetapp.Activities.AddCategoryActivity.EXTRA_NAME";
    public static final int RESULT_CODE = 100;
    public static final int ADD_REQUEST_CODE = 1;
    private EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        editTextName = findViewById(R.id.edit_text_name);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close);
        setTitle("add Category");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.save_category:
                saveCategory();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void saveCategory() {
        String name = editTextName.getText().toString();
        if (name.trim().isEmpty()) return;
        Intent intent = new Intent(AddCategoryActivity.this, MainActivity.class);
        intent.putExtra(EXTRA_NAME, name);
        setResult(CategoryFragment.RESULT_OK, intent);
        finish();
    }
}