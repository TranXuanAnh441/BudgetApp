package com.example.budgetapp.recyclerviewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.Database.ExpenseIncome.ExpenseIncome;
import com.example.budgetapp.R;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRCVAdapter extends RecyclerView.Adapter<ExpenseRCVAdapter.ExpenseViewHolder> implements Filterable {
    public List<ExpenseIncome> getExpenses() {
        return expenses;
    }

    private List<ExpenseIncome> expenses =new ArrayList<>();
    private List<ExpenseIncome> searchedList =new ArrayList<>();

    public void setExpenses(List<ExpenseIncome> expenses) {
        this.expenses = expenses;
        this.searchedList = expenses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpenseRCVAdapter.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseRCVAdapter.ExpenseViewHolder holder, int position) {

        if(position < expenses.size() && holder.textViewTitle != null) {
            ExpenseIncome currentExpense = expenses.get(position);
            holder.textViewDate.setText(currentExpense.getDate());
            holder.textViewTitle.setText(currentExpense.getTitle());
            holder.textViewAmount.setText(String.valueOf(currentExpense.getAmount()));
            holder.textViewDescription.setText(currentExpense.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearched = constraint.toString();
                if(strSearched.isEmpty()){
                    expenses = searchedList;
                }else{
                    List<ExpenseIncome> list = new ArrayList<>();
                    for(ExpenseIncome expense : searchedList){
                        if(expense.getTitle().toLowerCase().contains(strSearched.toLowerCase())
                            || expense.getDescription().toLowerCase().contains(strSearched.toLowerCase())
                            || expense.getDate().toLowerCase().contains(strSearched.toLowerCase())
                            || String.valueOf(expense.getAmount()).contains(strSearched.toLowerCase())){
                            list.add(expense);
                        }
                    }
                    expenses = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = expenses;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                expenses = (List<ExpenseIncome>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewDate;
        private TextView textViewTitle;
        private TextView textViewAmount;
        private TextView textViewDescription;
        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
        }
    }
}
