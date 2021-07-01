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

import com.example.budgetapp.Activities.AddExpenseIncomeActivity;
import com.example.budgetapp.Database.AppViewModel;
import com.example.budgetapp.Database.ExpenseIncome.ExpenseIncome;
import com.example.budgetapp.Activities.ExpenseIncomeRCVActivity;
import com.example.budgetapp.R;
import com.example.budgetapp.Adapters.ExpenseListAdapter;

import java.util.List;

public class ExpenseRecyclerViewFragment extends Fragment {
    private AppViewModel appViewModel;

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

        ExpenseListAdapter expenseListAdapter = new ExpenseListAdapter();

        recyclerView.setAdapter(expenseListAdapter);

        ExpenseIncomeRCVActivity expenseRecyclerViewActivity = (ExpenseIncomeRCVActivity) getActivity();
        String date = expenseRecyclerViewActivity.getDate();


        appViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AppViewModel.class);
        appViewModel.setDateFilter(date);
        appViewModel.getDateExpense().observe(getActivity(), new Observer<List<ExpenseIncome>>() {
            @Override
            public void onChanged(List<ExpenseIncome> expenses) {
                expenseListAdapter.submitList(expenses);
                }});

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        appViewModel.deleteExpenseIncome(expenseListAdapter.getExpenseAt(viewHolder.getAdapterPosition()));
                        Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                }).attachToRecyclerView(recyclerView);

        expenseListAdapter.setOnItemClickListener(new ExpenseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ExpenseIncome expense) {
                Intent intent = new Intent(getActivity(), AddExpenseIncomeActivity.class);
                intent.putExtra(AddExpenseIncomeActivity.EXTRA_TYPE_ID, expense.getTypeId());
                intent.putExtra(AddExpenseIncomeActivity.EXTRA_ID, expense.getId());
                intent.putExtra(AddExpenseIncomeActivity.EXTRA_AMOUNT, expense.getAmount());
                intent.putExtra(AddExpenseIncomeActivity.EXTRA_DATE, expense.getDate());
                intent.putExtra(AddExpenseIncomeActivity.EXTRA_TITLE, expense.getTitle());
                intent.putExtra(AddExpenseIncomeActivity.EXTRA_DESCRIPTION, expense.getDescription());
                intent.putExtra(AddExpenseIncomeActivity.EXTRA_CATEGORY, expense.getCategoryId());
                startActivityForResult(intent, AddExpenseIncomeActivity.UPDATE_REQUEST_CODE);
            }});}

    @Override
    public void onActivityResult(int request_code, int result_code, Intent data) {
        if (request_code == AddExpenseIncomeActivity.UPDATE_REQUEST_CODE && result_code == AddExpenseIncomeActivity.RESULT_OK) {
            long expense_id = data.getLongExtra(AddExpenseIncomeActivity.EXTRA_ID, -1);
            if (expense_id == -1 ) {
                return;
            }
            String title = data.getStringExtra(AddExpenseIncomeActivity.EXTRA_TITLE);
            String date = data.getStringExtra(AddExpenseIncomeActivity.EXTRA_DATE);
            String description = data.getStringExtra(AddExpenseIncomeActivity.EXTRA_DESCRIPTION);
            int amount = data.getIntExtra(AddExpenseIncomeActivity.EXTRA_AMOUNT, 1);
            int categoryId = data.getIntExtra(AddExpenseIncomeActivity.EXTRA_CATEGORY, 0);
            int typeId = data.getIntExtra(AddExpenseIncomeActivity.EXTRA_TYPE_ID, 0);
            ExpenseIncome expense = new ExpenseIncome(title, description, amount, date,typeId);
            expense.setId(expense_id);
            expense.setCategoryId(categoryId);
            appViewModel.updateExpenseIncome(expense);}
    }
}
