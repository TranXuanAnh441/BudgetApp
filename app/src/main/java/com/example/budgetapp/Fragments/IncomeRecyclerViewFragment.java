package com.example.budgetapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.AddExpenseActivity;
import com.example.budgetapp.ExpenseRecyclerViewActivity;
import com.example.budgetapp.R;
import com.example.budgetapp.expenseDatabase.Expense;
import com.example.budgetapp.expenseDatabase.ExpenseViewModel;
import com.example.budgetapp.incomeDatabase.Income;
import com.example.budgetapp.incomeDatabase.IncomeViewModel;
import com.example.budgetapp.recyclerviewAdapter.ExpenseAdapter;
import com.example.budgetapp.recyclerviewAdapter.IncomeAdapter;

import java.util.List;

public class IncomeRecyclerViewFragment extends Fragment {
    private IncomeViewModel incomeViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.income_rcv_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        androidx.recyclerview.widget.RecyclerView recyclerView = view.findViewById(R.id.income_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        IncomeAdapter incomeAdapter = new IncomeAdapter();

        recyclerView.setAdapter(incomeAdapter);

        ExpenseRecyclerViewActivity expenseRecyclerViewActivity = (ExpenseRecyclerViewActivity) getActivity();
        String date = expenseRecyclerViewActivity.getDate();

        incomeViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(IncomeViewModel.class);
        incomeViewModel.setFilter(date);
        incomeViewModel.getDateIncome().observe(getActivity(), new Observer<List<Income>>() {
            @Override
            public void onChanged(List<Income> incomes) {
                incomeAdapter.submitList(incomes);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                incomeViewModel.delete(incomeAdapter.getIncomeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }
}
