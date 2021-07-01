package com.example.budgetapp.Database.Category;

import com.anychart.chart.common.dataentry.DataEntry;

import java.util.List;

public interface IApiAccessResponse {
    void postResult(List<DataEntry> dataEntryList);
}
