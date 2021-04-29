package com.example.budgetapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetapp.DailyBalanceDatabase.DailyBalance;
import com.example.budgetapp.DailyBalanceDatabase.DailyBalanceViewModel;
import com.example.budgetapp.ExpenseDatabase.Expense;
import com.example.budgetapp.ExpenseDatabase.ExpenseViewModel;
import com.example.budgetapp.ExpenseIncomeRCVActivity;
import com.example.budgetapp.IncomeDatabase.IncomeViewModel;
import com.example.budgetapp.MainActivity;
import com.example.budgetapp.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    private CompactCalendarView compactCalendarView;
    private DailyBalanceViewModel dailyBalanceViewModel;
    private TextView expenseSumTextView;
    private TextView incomeSumTextView;
    private TextView monthTextView;
    private ExpenseViewModel expenseViewModel;
    private IncomeViewModel incomeViewModel;
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
        dailyBalanceViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(DailyBalanceViewModel.class);
        expenseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ExpenseViewModel.class);
        incomeViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(IncomeViewModel.class);
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
    }
    public void updateMonthSum(){
        expenseViewModel.setMonthFilter("%%%" + monthTextView.getText().toString());
        expenseViewModel.getMonthSum().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == null){expenseSumTextView.setText("Expense: 0");}
                else expenseSumTextView.setText("Expense: " + String.valueOf(integer));
            }
        });
        incomeViewModel.setMonthFilter("%%%" + monthTextView.getText().toString());
        incomeViewModel.getMonthSum().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == null){incomeSumTextView.setText("Income : 0");}
                else incomeSumTextView.setText("Income: "+ String.valueOf(integer));
            }
        });
    }
}
