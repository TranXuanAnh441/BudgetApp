package com.example.budgetapp.categoryDatabase;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.example.budgetapp.expenseDatabase.Expense;

import java.io.Serializable;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;



@Entity(tableName = "category_table")
public class Category implements Serializable {

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @PrimaryKey(autoGenerate = true)
    private int cid;

    public String getName() {
        return name;
    }

    private String name;


    public Category(String name) {
        this.name = name;
    }
}
