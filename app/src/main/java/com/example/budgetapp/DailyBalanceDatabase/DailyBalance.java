package com.example.budgetapp.DailyBalanceDatabase;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "balance_table")
public class DailyBalance implements Serializable {

    public DailyBalance(String date, int income, int expense) {
        this.date = date;
        this.income = income;
        this.expense = expense;
    }

    public int getDid() {
        return Did;
    }

    public void setDid(int did) {
        Did = did;
    }

    @PrimaryKey (autoGenerate = true)
    public int Did;

    public String getDate() {
        return date;
    }

    public String date;

    public int getIncome() {
        return income;
    }

    public int getExpense() {
        return expense;
    }

    public int income;

    public void setIncome(int income) {
        this.income = income;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public int expense;
}
