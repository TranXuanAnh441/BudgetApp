package com.example.budgetapp.Activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SearchView;

import com.example.budgetapp.Fragments.CategoryFragment;
import com.example.budgetapp.Fragments.CalendarFragment;
import com.example.budgetapp.Fragments.MonthlyFragment;
import com.example.budgetapp.R;
import com.example.budgetapp.recyclerviewAdapter.CategoryRCVAdapter;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public CategoryRCVAdapter getCategoryRCVAdapter() {
        return categoryRCVAdapter;
    }
    CategoryRCVAdapter categoryRCVAdapter;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DrawerLayout drawer = (DrawerLayout) inflater.inflate(R.layout.decor, null); // "null" is important.

        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);
        FrameLayout container = (FrameLayout) drawer.findViewById(R.id.fragment_container); // This is the container we defined just now.
        container.addView(child);

        decor.addView(drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu);
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setDisplayShowTitleEnabled( false );
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CalendarFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_calendar);
        }
        categoryRCVAdapter = new CategoryRCVAdapter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {
            case android.R.id.home:
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_search:
                Intent newIntent = new Intent(this, SearchActivity.class);
                startActivity(newIntent);

            default:
                break;
        }
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_category:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CategoryFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_calendar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CalendarFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_monthly:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MonthlyFragment()).addToBackStack(null).commit();
            break;
            }
        return true;
}
    @Override
    public void onActivityResult(int request_code, int result_code, Intent data) {
        super.onActivityResult(request_code, result_code, data);}
}
