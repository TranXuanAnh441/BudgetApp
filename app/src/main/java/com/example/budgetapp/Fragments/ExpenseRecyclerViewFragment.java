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
import com.example.budgetapp.recyclerviewAdapter.ExpenseAdapter;

import java.util.List;

public class ExpenseRecyclerViewFragment extends Fragment {
    private ExpenseViewModel expenseViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.expense_rcv_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        androidx.recyclerview.widget.RecyclerView recyclerView = view.findViewById(R.id.expense_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        ExpenseAdapter expenseAdapter = new ExpenseAdapter();

        recyclerView.setAdapter(expenseAdapter);

        ExpenseRecyclerViewActivity expenseRecyclerViewActivity = (ExpenseRecyclerViewActivity) getActivity();
        String date = expenseRecyclerViewActivity.getDate();

        expenseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ExpenseViewModel.class);
        expenseViewModel.setFilter(date);
        expenseViewModel.getDateExpense().observe(getActivity(), new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                expenseAdapter.submitList(expenses);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                expenseViewModel.delete(expenseAdapter.getExpenseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        expenseAdapter.setOnItemClickListener(new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Expense expense) {
                Intent intent = new Intent(getActivity(), AddExpenseActivity.class);
                intent.putExtra(AddExpenseActivity.EXPENSE_ID, expense.getEid());
                intent.putExtra(AddExpenseActivity.EXPENSE_AMOUNT, expense.getAmount());
                intent.putExtra(AddExpenseActivity.EXPENSE_DATE, expense.getDate());
                intent.putExtra(AddExpenseActivity.EXPENSE_TITLE, expense.getTitle());
                intent.putExtra(AddExpenseActivity.EXPENSE_DESCRIPTION, expense.getDescription());
                intent.putExtra(AddExpenseActivity.EXTRA_CATEGORY, expense.getCategoryId());
                startActivityForResult(intent, AddExpenseActivity.UPDATE_REQUEST_CODE);
            }
        });


    }}