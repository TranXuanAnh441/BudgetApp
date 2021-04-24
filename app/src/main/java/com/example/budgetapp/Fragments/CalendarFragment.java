package com.example.budgetapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetapp.DailyBalanceDatabase.DailyBalance;
import com.example.budgetapp.DailyBalanceDatabase.DailyBalanceViewModel;
import com.example.budgetapp.ExpenseIncomeRCVActivity;
import com.example.budgetapp.R;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.HashMap;

public class CalendarFragment extends Fragment {
    private CustomCalendar customCalendar;
    public static final String DATE_VALUE = "com.example.budgetapp.Fragments.CalendarFragment.DATE_VALUE";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.expense_fragment, container, false);
        return v;

}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        customCalendar = view.findViewById(R.id.calendarView);
        HashMap<Object, Property> descHashMap = new HashMap<>();
        Property defaultProperty = new Property();
        defaultProperty.layoutResource = R.layout.default_view;
        defaultProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("default", defaultProperty);

        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("current", currentProperty);

        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present;
        presentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("present", presentProperty);

        customCalendar.setMapDescToProp(descHashMap);
        HashMap<Integer, Object> dateHashMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        dateHashMap.put(11,"current");
        dateHashMap.put(1,"present");
        dateHashMap.put(2,"present");
        dateHashMap.put(3,"present");
        customCalendar.setDate(calendar, dateHashMap);
        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                String date = selectedDate.get(Calendar.DAY_OF_MONTH) + "/"
                        + (selectedDate.get(Calendar.MONTH)+1) + "/"
                        + selectedDate.get(Calendar.YEAR);
                Intent intent = new Intent(view.getContext(), ExpenseIncomeRCVActivity.class);
                intent.putExtra(DATE_VALUE, date);
                startActivity(intent);
            }
        });
    }


}
