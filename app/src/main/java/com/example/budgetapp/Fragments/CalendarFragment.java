package com.example.budgetapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetapp.DailyBalanceDatabase.DailyBalance;
import com.example.budgetapp.DailyBalanceDatabase.DailyBalanceViewModel;
import com.example.budgetapp.ExpenseDatabase.ExpenseViewModel;
import com.example.budgetapp.ExpenseIncomeRCVActivity;
import com.example.budgetapp.IncomeDatabase.IncomeViewModel;
import com.example.budgetapp.R;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.HashMap;

public class CalendarFragment extends Fragment {
    private CustomCalendar customCalendar;
    private DailyBalanceViewModel dailyBalanceViewModel;
    private TextView expenseSumTextView;
    private TextView incomeSumTextView;
    private ExpenseViewModel expenseViewModel;
    private IncomeViewModel incomeViewModel;
    private Integer count = 0;
    private String clicked_date;
    public static final String DATE_VALUE = "com.example.budgetapp.Fragments.CalendarFragment.DATE_VALUE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar_fragment, container, false);
        return v;

}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dailyBalanceViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(DailyBalanceViewModel.class);
        expenseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ExpenseViewModel.class);
        incomeViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(IncomeViewModel.class);

        customCalendar = view.findViewById(R.id.calendarView);
        expenseSumTextView = view.findViewById(R.id.expense_sum);
        incomeSumTextView = view.findViewById(R.id.income_sum);

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
        customCalendar.setDate(calendar, dateHashMap);

        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                String date = selectedDate.get(Calendar.DAY_OF_MONTH) + "/"
                        + (selectedDate.get(Calendar.MONTH)+1) + "/"
                        + selectedDate.get(Calendar.YEAR);
                if(clicked_date != null && clicked_date.equals(date) ){
                    count ++;
                }
                else count = 1;
                clicked_date = date;

                expenseViewModel.setFilter(date);
                expenseViewModel.getDateSum().observe(getActivity(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if(integer == null){expenseSumTextView.setText("Expense: 0");}
                        else{expenseSumTextView.setText("Expense: " + String.valueOf(integer));}
                    }
                });
                incomeViewModel.setFilter(date);
                incomeViewModel.getDateSum().observe(getActivity(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if(integer == null){incomeSumTextView.setText("Income : 0");}
                        else{incomeSumTextView.setText("Income: " + String.valueOf(integer));}
                    }
                });
                if(count == 2){ count = 0;
                Intent intent = new Intent(view.getContext(), ExpenseIncomeRCVActivity.class);
                intent.putExtra(DATE_VALUE, date);
                startActivity(intent);}
            }
        });
    }


}
