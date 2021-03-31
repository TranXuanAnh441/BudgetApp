package com.example.budgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

public class CalendarActivity extends AppCompatActivity {
    public static final String DATE_VALUE = "com.example.budgetapp.CalendarActivity.DATE_VALUE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        CalendarView calendarView=findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String date =  dayOfMonth + "/" + String.valueOf(month + 1) + "/" + year;
                Intent intent = new Intent(view.getContext(), ExpenseRecyclerViewActivity.class);
                intent.putExtra(DATE_VALUE, date);
                startActivity(intent);
            }
        });
    }
}