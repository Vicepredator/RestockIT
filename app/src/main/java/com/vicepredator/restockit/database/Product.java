package com.vicepredator.restockit.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products_table")
public class Product {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idProduct")
    public String code;

    @ColumnInfo(name = "Name")
    public String productName;

    @ColumnInfo(name = "minQty")
    public int minQty;

    @ColumnInfo(name = "avlQty")
    public int avlQty;

    public Product() {
        code = null;
    }
}


