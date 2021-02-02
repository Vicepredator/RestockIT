package com.vicepredator.restockit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vicepredator.restockit.database.Product;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHoler> {

    Context ctx;
    List<Product> products;

    public MyAdapter(Context c, List<Product> p){
        ctx = c;
        products = p;
    }

    @NonNull
    @Override
    public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.shoplistrow, parent,false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {
        holder.name.setText(products.get(position).productName);
        holder.qty.setText(products.get(position).minQty-products.get(position).avlQty);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class MyViewHoler extends RecyclerView.ViewHolder {

        TextView name,qty;

        public MyViewHoler(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lblName);
            qty = itemView.findViewById(R.id.lblCode);
        }
    }
}
