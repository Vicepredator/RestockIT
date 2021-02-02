package com.vicepredator.restockit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.vicepredator.restockit.database.*;

import java.util.List;

public class ShopList extends AppCompatActivity {

    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        ProductsDao productsDB = dbHandler.getDatabase(this).ProductsDao();
        List<Product> productsNeeded = productsDB.getNeededProducts();
        recycler = findViewById(R.id.cardsRecycler);
        MyAdapter adapter = new MyAdapter(this,productsNeeded);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}