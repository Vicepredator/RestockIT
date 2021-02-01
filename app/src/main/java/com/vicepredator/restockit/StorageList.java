package com.vicepredator.restockit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vicepredator.restockit.database.Product;
import com.vicepredator.restockit.database.ProductsDao;
import com.vicepredator.restockit.database.dbHandler;

import java.util.List;

public class StorageList extends AppCompatActivity {

    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_list);
        ProductsDao productsDB = dbHandler.getDatabase(this).ProductsDao();
        List<Product> productsNeeded = productsDB.getProducts();
        recycler = findViewById(R.id.cardsRecycler);
        MyAdapter2 adapter = new MyAdapter2(this,productsNeeded);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}