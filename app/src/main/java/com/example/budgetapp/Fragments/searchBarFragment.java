package com.example.budgetapp.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.CategoryDatabase.Category;
import com.example.budgetapp.ExpenseDatabase.Expense;
import com.example.budgetapp.ExpenseDatabase.ExpenseViewModel;
import com.example.budgetapp.R;
import com.example.budgetapp.recyclerviewAdapter.ExpenseAdapter;

import java.util.ArrayList;
import java.util.List;

public class searchBarFragment extends Fragment {
    private ArrayList<Expense> expenseList;
    private ExpenseAdapter expenseAdapter;
    private ExpenseViewModel expenseViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        return view;}
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText editText = view.findViewById(R.id.searchEditText);

        androidx.recyclerview.widget.RecyclerView recyclerView = view.findViewById(R.id.searchRCV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        expenseAdapter = new ExpenseAdapter();
        recyclerView.setAdapter(expenseAdapter);

        expenseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpense().observe(getActivity(), new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                expenseAdapter.submitList(expenses);
            }});
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });
    }

    private void search(String text) {
        ArrayList<Expense> searchList = new ArrayList<>();
        for(Expense expense : expenseAdapter.getCurrentList()){
            if(expense.getTitle().toLowerCase().startsWith(text.toLowerCase()) || expense.getDescription().toLowerCase().startsWith(text.toLowerCase()) || expense.getDate().toLowerCase().startsWith(text.toLowerCase())){
                searchList.add(expense);
            }
        }
        expenseAdapter.submitList(searchList);
    }
}
