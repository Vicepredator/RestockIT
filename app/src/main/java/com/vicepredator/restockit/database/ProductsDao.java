package com.vicepredator.restockit.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ProductsDao {

    @Query("SELECT * FROM products_table WHERE idProduct = :productCode")
    List<Product> getProductFromCode(String productCode);

    @Insert
    void insertProduct(Product p);

    @Query("SELECT * FROM products_table WHERE avlQty < minQty")
    List<Product> getNeededProducts();

    @Query("SELECT * FROM products_table")
    List<Product> getProducts();

    @Update
    void updateProduct(Product p);

    @Query("SELECT * FROM products_table WHERE idProduct = :productCode LIMIT 1")
    Product getProductByCode(String productCode);

    @Delete
    void deleteProduct(Product p);
}