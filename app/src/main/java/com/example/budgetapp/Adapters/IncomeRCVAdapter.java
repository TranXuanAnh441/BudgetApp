package com.example.budgetapp.Adapters;

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

public class IncomeRCVAdapter extends RecyclerView.Adapter<IncomeRCVAdapter.IncomeViewHolder> implements Filterable {

    public List<ExpenseIncome> getIncomes() {
        return incomes;
    }

    private List<ExpenseIncome> incomes = new ArrayList<>();
    private List<ExpenseIncome> searchedList = new ArrayList<>();

    public void setIncomes(List<ExpenseIncome> incomes) {
        this.incomes = incomes;
        this.searchedList = incomes;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public IncomeRCVAdapter.IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_item, parent, false);
        return new IncomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeRCVAdapter.IncomeViewHolder holder, int position) {

        if(position < incomes.size() && holder.textViewTitle != null){
            ExpenseIncome currentIncome = incomes.get(position);
            holder.textViewDate.setText(currentIncome.getDate());
        holder.textViewTitle.setText(currentIncome.getTitle());
        holder.textViewAmount.setText(String.valueOf(currentIncome.getAmount()));
        holder.textViewDescription.setText(currentIncome.getDescription());}
    }

    @Override
    public int getItemCount() {
        return incomes.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearched = constraint.toString();
                if(strSearched.isEmpty()){
                    incomes = searchedList;
                }else{
                    List<ExpenseIncome> list = new ArrayList<>();
                    for(ExpenseIncome income : searchedList){
                        if(income.getTitle().toLowerCase().contains(strSearched.toLowerCase())
                                || income.getDescription().toLowerCase().contains(strSearched.toLowerCase())
                                || income.getDate().toLowerCase().contains(strSearched.toLowerCase())
                                || String.valueOf(income.getAmount()).contains(strSearched.toLowerCase())){
                            list.add(income);
                        }
                    }
                    incomes = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = incomes;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                incomes = (List<ExpenseIncome>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public class IncomeViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewDate;
        private TextView textViewTitle;
        private TextView textViewAmount;
        private TextView textViewDescription;
        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
            textViewDescription = itemView.findViewById(R.id.text_view_description);

        }
    }
}
