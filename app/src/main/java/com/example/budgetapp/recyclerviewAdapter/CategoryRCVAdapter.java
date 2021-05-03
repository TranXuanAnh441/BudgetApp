package com.example.budgetapp.recyclerviewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.R;
import com.example.budgetapp.CategoryDatabase.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryRCVAdapter extends RecyclerView.Adapter< CategoryRCVAdapter.CategoryHolder> implements Filterable {
    public List<Category> getCategories() {
        return categories;
    }

    private List<Category> categories = new ArrayList<>();
    private List<Category> searchedCategories = new ArrayList<>();
    private OnItemClickListener listener;

    public Category getCategoryAt(int position) {
        return categories.get(position);
    }
    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new CategoryHolder(itemView);
    }
    public void setCategories (List < Category > categories) {
        this.categories = categories;
        this.searchedCategories = categories;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category currentCategory = categories.get(position);
        holder.textViewName.setText(currentCategory.getName());
    }

    @Override
    public int getItemCount() { return categories.size(); }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearched = constraint.toString();
                if(strSearched.isEmpty()){
                    categories = searchedCategories;
                }else{
                    List<Category> list = new ArrayList<>();
                    for(Category category: searchedCategories){
                        if(category.getName().toLowerCase().contains(strSearched.toLowerCase())){
                            list.add(category);
                        }
                    }
                    categories = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = categories;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                categories = (List<Category>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;

        public CategoryHolder(View itemview) {
            super(itemview);
            textViewName = itemview.findViewById(R.id.text_view_name);

            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(categories.get(position));
                    }
                });
            }}

    public interface OnItemClickListener {
        void onItemClick(Category category);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    }
