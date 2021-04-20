package com.example.budgetapp.CategoryDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


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
