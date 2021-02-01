package com.vicepredator.restockit.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import java.util.List;

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
}


