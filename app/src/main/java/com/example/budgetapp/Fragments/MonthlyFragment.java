package com.example.budgetapp.Fragments;

import android.os.Bundle;
import android.os.HandlerThread;
import android.telephony.IccOpenLogicalChannelResponse;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.budgetapp.AppDatabase;
import com.example.budgetapp.CategoryDatabase.Category;
import com.example.budgetapp.CategoryDatabase.CategoryDao;
import com.example.budgetapp.CategoryDatabase.CategoryViewModel;
import com.example.budgetapp.R;
import com.example.budgetapp.recyclerviewAdapter.CategoryAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import static android.content.ContentValues.TAG;


public class MonthlyFragment extends Fragment {
    AnyChartView anyChartView;
    private SimpleDateFormat monthFormatYear = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    final private AppDatabase categoryDatabase = AppDatabase.getInstance(getActivity());
    final CountDownLatch latch = new CountDownLatch(1);
    private List<String> categoryNames = new ArrayList<>();
    private List<Integer> categoryIds = new ArrayList<>();
    private List<Integer> sum = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monthly_fragment, container, false);
        return view;}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        anyChartView = view.findViewById(R.id.chartView);
        Calendar cal = Calendar.getInstance();
        String date = monthFormatYear.format(cal.getTime());
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Category> categoryList = categoryDatabase.categoryDao().getListCategory();
                for(Category category : categoryList){
                    Integer monthSum= categoryDatabase.categoryDao().getMonthlyCategorySum(category.getCid(), "%%%" + date);
                    categoryNames.add(category.getName());
                    categoryIds.add(category.getCid());
                    sum.add(monthSum);}
                latch.countDown();

            }
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setUpPie();
    }


    public void setUpPie(){
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for(int i = 0; i < categoryNames.size(); i ++){
            dataEntries.add(new ValueDataEntry(categoryNames.get(i), sum.get(i)));
        }
        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }
}

