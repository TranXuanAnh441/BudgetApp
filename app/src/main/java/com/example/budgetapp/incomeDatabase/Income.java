package com.example.budgetapp.incomeDatabase;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import com.example.budgetapp.categoryDatabase.Category;


        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.room.Entity;
        import androidx.room.ForeignKey;
        import androidx.room.PrimaryKey;

        import com.example.budgetapp.categoryDatabase.Category;

        import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "income_table")
public class Income {
    public int getIid() {
        return Iid;
    }

    public void setIid(int iid) {
        Iid = iid;
    }

    @PrimaryKey(autoGenerate = true)
    private int Iid;

    private String title;

    private String description;

    private int amount;

    private String date;

    @ForeignKey(entity = Category.class,
            parentColumns = "cid",
            childColumns = "categoryId",
            onDelete = CASCADE
    )

    public Income(String title, String description, int amount, String date) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
