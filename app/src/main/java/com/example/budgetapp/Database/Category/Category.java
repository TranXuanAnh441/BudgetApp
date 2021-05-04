package com.example.budgetapp.Database.Category;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;



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

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        @Nullable
        public byte[] getCategoryImage() {
            return categoryImage;
        }

        public void setCategoryImage(@Nullable byte[] categoryImage) {
            this.categoryImage = categoryImage;
        }

         @Nullable
         @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
            private byte[] categoryImage;

            //Constructor
            public Category(String name) {
            this.name = name;
        }
    }

