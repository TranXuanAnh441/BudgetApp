package com.example.budgetapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.Database.ExpenseIncome.ExpenseIncome;
import com.example.budgetapp.R;

public class IncomeListAdapter extends ListAdapter<ExpenseIncome, IncomeListAdapter.IncomeHolder> {
    private IncomeListAdapter.OnItemClickListener listener;

    private static final DiffUtil.ItemCallback<ExpenseIncome> DIFF_CALLBACK = new DiffUtil.ItemCallback<ExpenseIncome>() {
        @Override
        public boolean areItemsTheSame(@NonNull ExpenseIncome oldItem, @NonNull ExpenseIncome newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ExpenseIncome oldItem, @NonNull ExpenseIncome newItem) {
            return oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getAmount() == newItem.getAmount();
        }};
    public IncomeListAdapter() {
        super(DIFF_CALLBACK);
    }

    public ExpenseIncome getIncomeAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public IncomeListAdapter.IncomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_item, parent, false);
        return new IncomeListAdapter.IncomeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeListAdapter.IncomeHolder holder, int position) {
        ExpenseIncome currentIncome = getItem(position);
        holder.textViewDate.setText(currentIncome.getDate());
        holder.textViewTitle.setText(currentIncome.getTitle());
        holder.textViewAmount.setText(String.valueOf(currentIncome.getAmount()));
        holder.textViewDescription.setText(currentIncome.getDescription());
    }
    class IncomeHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private TextView textViewDate;
        private TextView textViewTitle;
        private TextView textViewAmount;
        private TextView textViewDescription;


        public IncomeHolder(View itemView){
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
        void onItemClick(ExpenseIncome Expenseincome);
    }
    public void setOnItemClickListener(IncomeListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
