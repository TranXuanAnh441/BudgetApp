package com.example.budgetapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetapp.Database.AppViewModel;
import com.example.budgetapp.Activities.ExpenseIncomeRCVActivity;
import com.example.budgetapp.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    private CompactCalendarView compactCalendarView;
    private TextView expenseSumTextView;
    private TextView incomeSumTextView;
    private TextView monthTextView;
    private AppViewModel appViewModel;
    private Integer count = 0;
    private String clicked_date;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault());
    private SimpleDateFormat monthFormatYear = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    public static final String DATE_VALUE = "com.example.budgetapp.Fragments.CalendarFragment.DATE_VALUE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar_fragment, container, false);
        return v;

}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        appViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AppViewModel.class);

        monthTextView = view.findViewById(R.id.monthTextView);
        expenseSumTextView = view.findViewById(R.id.expense_sum);
        incomeSumTextView = view.findViewById(R.id.income_sum);

        Calendar cal = Calendar.getInstance();
        monthTextView.setText(monthFormatYear.format(cal.getTime()));
        updateMonthSum();

        compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                String date = dateFormat.format(dateClicked);
                if(clicked_date != null && clicked_date.equals(date) ){
                    count ++;
                }
                else count = 1;
                clicked_date = date;
                updateDateSum(date);

                if (count == 2){ count = 0;
                    Intent intent = new Intent(view.getContext(), ExpenseIncomeRCVActivity.class);
                    intent.putExtra(DATE_VALUE, date);
                    startActivity(intent);}
            }
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthTextView.setText(monthFormatYear.format(firstDayOfNewMonth));
            }
        });
    }


    public void updateDateSum(String date){
        appViewModel.setDateFilter(date);
        appViewModel.getSumDateExpense().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == null){expenseSumTextView.setText("Expense: 0");}
                else{expenseSumTextView.setText("Expense: " + String.valueOf(integer));}
            }
        });

        appViewModel.getSumDateIncome().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == null){incomeSumTextView.setText("Income : 0");}
                else{incomeSumTextView.setText("Income: " + String.valueOf(integer));}
            }
        });
    }
    public void updateMonthSum(){
        appViewModel.setMonthFilter("%%%" + monthTextView.getText().toString());
        appViewModel.getSumMonthExpense().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == null){expenseSumTextView.setText("Expense: 0");}
                else expenseSumTextView.setText("Expense: " + String.valueOf(integer));
            }
        });

        appViewModel.getSumMonthIncome().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == null){incomeSumTextView.setText("Income : 0");}
                else incomeSumTextView.setText("Income: "+ String.valueOf(integer));
            }
        });
    }
}
