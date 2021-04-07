package com.example.budgetapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.example.budgetapp.Fragments.CategoryFragment;
import com.example.budgetapp.Fragments.ExpenseFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.main_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ExpenseFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_expense);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_category:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CategoryFragment()).commit();
                break;
            case R.id.nav_expense:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ExpenseFragment()).commit();
                break;
            }
        DrawerLayout drawer = findViewById(R.id.main_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
}}