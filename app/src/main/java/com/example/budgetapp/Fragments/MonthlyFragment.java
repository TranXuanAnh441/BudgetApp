package com.example.budgetapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.budgetapp.AppDatabase;
import com.example.budgetapp.Database.AppViewModel;
import com.example.budgetapp.Database.Category.Category;
import com.example.budgetapp.Database.Category.IApiAccessResponse;
import com.example.budgetapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;


public class MonthlyFragment extends Fragment {
    AnyChartView anyChartView;
    private SimpleDateFormat monthFormatYear = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    private AppViewModel appViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monthly_fragment, container, false);
        return view;}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AppViewModel.class);
        anyChartView = view.findViewById(R.id.chartView);
        Calendar cal = Calendar.getInstance();
        String date = monthFormatYear.format(cal.getTime());
        appViewModel.getMonthPie("%%%" + date);
        appViewModel.getMonthPieResult().observe(getActivity(), new Observer<List<DataEntry>>() {
            @Override
            public void onChanged(List<DataEntry> dataEntries) {
                if(dataEntries == null){
                    Toast.makeText(getActivity(), "null", Toast.LENGTH_LONG).show();
                }
                Pie pie = AnyChart.pie();
                pie.data(dataEntries);
                anyChartView.setChart(pie);
            }
        });
    }
}

