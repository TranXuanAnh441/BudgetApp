package com.example.budgetapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.budgetapp.R;

import java.util.ArrayList;
import java.util.List;

public class MonthlyFragment extends Fragment {
    AnyChartView anyChartView;
    private String[] character = {"A", "B", "C","D"};
    private Integer[] number = {100,200,300,400};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monthly_fragment, container, false);
        return view;}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anyChartView = view.findViewById(R.id.chartView);
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntryList = new ArrayList<>();

        for(int i = 0; i < character.length; i ++){
            dataEntryList.add(new ValueDataEntry(character[i], number[i]));
        }
        pie.data(dataEntryList);
        anyChartView.setChart(pie);
    }
}
