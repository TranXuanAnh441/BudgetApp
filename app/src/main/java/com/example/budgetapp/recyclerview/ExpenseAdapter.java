package com.example.budgetapp.recyclerview;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.R;
import com.example.budgetapp.database.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends ListAdapter<Expense, ExpenseAdapter.ExpenseHolder> {
    private OnItemClickListener listener;
    private static final DiffUtil.ItemCallback<Expense> DIFF_CALLBACK = new DiffUtil.ItemCallback<Expense>() {
        @Override
        public boolean areItemsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getAmount() == newItem.getAmount();
        }};

    public ExpenseAdapter() {
        super(DIFF_CALLBACK);
    }

    public Expense getExpenseAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        return new ExpenseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        Expense currentExpense = getItem(position);
        holder.textViewDate.setText(currentExpense.getDate());
        holder.textViewTitle.setText(currentExpense.getTitle());
        holder.textViewAmount.setText(String.valueOf(currentExpense.getAmount()));
        holder.textViewDescription.setText(currentExpense.getDescription());
    }


    class ExpenseHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private TextView textViewDate;
        private TextView textViewTitle;
        private TextView textViewAmount;
        private TextView textViewDescription;


        public ExpenseHolder(View itemView){
            super(itemView);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
            textViewDescription = itemView.findViewById(R.id.text_view_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                }}
            });
    }}

    public interface OnItemClickListener {
        void onItemClick(Expense expense);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
