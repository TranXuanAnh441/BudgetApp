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
import com.example.budgetapp.DailyBalanceDatabase.DailyBalanceViewModel;
import com.example.budgetapp.ExpenseIncomeRCVActivity;
import com.example.budgetapp.R;
import com.example.budgetapp.IncomeDatabase.Income;
import com.example.budgetapp.IncomeDatabase.IncomeViewModel;
import com.example.budgetapp.recyclerviewAdapter.IncomeListAdapter;

import java.util.List;

public class IncomeRecyclerViewFragment extends Fragment {
    private IncomeViewModel incomeViewModel;
    private DailyBalanceViewModel dailyBalanceViewModel;
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

        IncomeListAdapter incomeListAdapter = new IncomeListAdapter();

        recyclerView.setAdapter(incomeListAdapter);

        ExpenseIncomeRCVActivity expenseRecyclerViewActivity = (ExpenseIncomeRCVActivity) getActivity();
        String date = expenseRecyclerViewActivity.getDate();

        incomeViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(IncomeViewModel.class);
        dailyBalanceViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(DailyBalanceViewModel.class);

        incomeViewModel.setFilter(date);
        incomeViewModel.getDateIncome().observe(getActivity(), new Observer<List<Income>>() {
            @Override
            public void onChanged(List<Income> incomes) {
                incomeListAdapter.submitList(incomes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                incomeViewModel.delete(incomeListAdapter.getIncomeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

      incomeListAdapter.setOnItemClickListener(new IncomeListAdapter.OnItemClickListener() {
          @Override
          public void onItemClick(Income income) {
              Intent intent = new Intent(getActivity(), AddExpenseActivity.class);
              intent.putExtra(AddExpenseActivity.INCOME_ID, income.getIid());
              intent.putExtra(AddExpenseActivity.INCOME_AMOUNT, income.getAmount());
              intent.putExtra(AddExpenseActivity.INCOME_DATE, income.getDate());
              intent.putExtra(AddExpenseActivity.INCOME_TITLE, income.getTitle());
              intent.putExtra(AddExpenseActivity.INCOME_DESCRIPTION, income.getDescription());
              startActivityForResult(intent, AddExpenseActivity.UPDATE_REQUEST_CODE);
          }
    });
}
    @Override
    public void onActivityResult(int request_code, int result_code, Intent data) {
        if (request_code == AddExpenseActivity.UPDATE_REQUEST_CODE && result_code == AddExpenseActivity.INCOME_RESULT){
            int income_id = data.getIntExtra(AddExpenseActivity.INCOME_ID, -1);
            if (income_id == -1 ) {
                return;
            }
            String title = data.getStringExtra(AddExpenseActivity.INCOME_TITLE);
            String date = data.getStringExtra(AddExpenseActivity.INCOME_DATE);
            String description = data.getStringExtra(AddExpenseActivity.INCOME_DESCRIPTION);
            int amount = data.getIntExtra(AddExpenseActivity.INCOME_AMOUNT, 1);
            Income income = new Income(title, description, amount, date);
            income.setIid(income_id);
            incomeViewModel.update(income);
        }
}}


