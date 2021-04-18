package com.example.budgetapp.recyclerviewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.budgetapp.R;
import com.example.budgetapp.expenseDatabase.Expense;
import com.example.budgetapp.incomeDatabase.Income;

public class IncomeAdapter extends ListAdapter<Income, IncomeAdapter.IncomeHolder> {
    private IncomeAdapter.OnItemClickListener listener;

    private static final DiffUtil.ItemCallback<Income> DIFF_CALLBACK = new DiffUtil.ItemCallback<Income>() {
        @Override
        public boolean areItemsTheSame(@NonNull Income oldItem, @NonNull Income newItem) {
            return oldItem.getIid() == newItem.getIid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Income oldItem, @NonNull Income newItem) {
            return oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getAmount() == newItem.getAmount();
        }};
    public IncomeAdapter() {
        super(DIFF_CALLBACK);
    }

    public Income getIncomeAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public IncomeAdapter.IncomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_item, parent, false);
        return new IncomeAdapter.IncomeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeAdapter.IncomeHolder holder, int position) {
        Income currentIncome = getItem(position);
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
        void onItemClick(Income income);
    }
    public void setOnItemClickListener(IncomeAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
