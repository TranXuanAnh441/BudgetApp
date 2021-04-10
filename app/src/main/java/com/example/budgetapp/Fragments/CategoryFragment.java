package com.example.budgetapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.AddCategoryActivity;
import com.example.budgetapp.AddExpenseActivity;
import com.example.budgetapp.CategoryRecyclerViewActivity;
import com.example.budgetapp.ExpenseRecyclerViewActivity;
import com.example.budgetapp.MainActivity;
import com.example.budgetapp.R;
import com.example.budgetapp.categoryDatabase.Category;
import com.example.budgetapp.categoryDatabase.CategoryViewModel;
import com.example.budgetapp.recyclerviewAdapter.CategoryAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class CategoryFragment extends Fragment {
    public static final int UPDATE_CATEGORY = 2;
    public static final int ADD_CATEGORY = 1;
    public static final int RESULT_OK = 100;
    public static final String CATEGORY = "com.example.budgetapp.Fragments.CATEGORY";
    private CategoryViewModel categoryViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        return view;}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton buttonAddCategory = view.findViewById(R.id.button_add_category);
        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final CategoryAdapter categoryAdapter = new CategoryAdapter();
        recyclerView.setAdapter(categoryAdapter);

        categoryViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(CategoryViewModel.class);
        categoryViewModel.getAllCategory().observe(getActivity(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryAdapter.setCategories(categories);
            }
        });

        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Category category) {
                Intent categoryIntent = new Intent(getActivity(), AddCategoryActivity.class);
                categoryIntent.putExtra(CATEGORY, (Serializable) category);
                getActivity().setResult(RESULT_OK, categoryIntent);
                getActivity().finish();
            }
        });
    }
}
