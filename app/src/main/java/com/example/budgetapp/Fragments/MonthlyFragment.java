package com.example.budgetapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.budgetapp.AppDatabase;
import com.example.budgetapp.Database.AppDao;
import com.example.budgetapp.Database.AppViewModel;
import com.example.budgetapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;


public class MonthlyFragment extends Fragment {
    AnyChartView anyChartView;
    private TextView monthTextView;
    private SimpleDateFormat monthFormatYear = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    private AppViewModel appViewModel;
    private String date;
    private Button drawGraphButton;
    private AppDao appDao = AppDatabase.getInstance(getContext()).appDao();
    private Pie pie;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monthly_fragment, container, false);
        pie = AnyChart.pie();
        anyChartView = view.findViewById(R.id.chartView);
        anyChartView.setChart(pie);
        return view;}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        monthTextView = view.findViewById(R.id.monthTextView);

        Calendar cal = Calendar.getInstance();
        date = monthFormatYear.format(cal.getTime());
        monthTextView.setText(date);
        drawGraphButton = view.findViewById(R.id.drawGraphButton);


        drawGraphButton.setOnClickListener(new View.OnClickListener() {
            CountDownLatch latch = new CountDownLatch(1);
            List<DataEntry> dataEntries = new ArrayList<>();
            @Override
            public void onClick(View v) {
                Thread thread = new Thread() {
                    public void run(){
                        List<Integer> sum = new ArrayList<>();
                        List<String> categoryNames = new ArrayList<>();
                        List<Integer> categoryIds = appDao.getMonthCategory("%%%"+date);
                        for(Integer ids : categoryIds){
                            sum.add(appDao.getMonthCategorySum(ids));
                            categoryNames.add(appDao.getCategoryNames(ids));
                        }
                        for(int i = 0; i < categoryNames.size(); i ++){
                            dataEntries.add(new ValueDataEntry(categoryNames.get(i), sum.get(i)));
                        }
                        latch.countDown();
                    }
                };
                thread.start();
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pie.data(dataEntries);
                dataEntries.clear();
            }});

        ImageView imgNextMonth = view.findViewById(R.id.nextMonthImg);
        imgNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                date =  monthFormatYear.format(cal.getTime());
                monthTextView.setText(date);
            }
        });
        ImageView imgPrevMonth = view.findViewById(R.id.prevMonthImg);
        imgPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                date = monthFormatYear.format(cal.getTime());
                monthTextView.setText(date);}

        });
    }
}

