package com.vicepredator.restockit;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vicepredator.restockit.database.Product;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHoler> {

    Context ctx;
    List<Product> products;

    public MyAdapter2(Context c, List<Product> p){
        ctx = c;
        products = p;
    }

    @NonNull
    @Override
    public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.storagelistrow, parent,false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {
        holder.name.setText(products.get(position).productName);
        holder.minqty.setText(products.get(position).minQty+"");
        holder.avlqty.setText(products.get(position).avlQty+"");
        holder.code.setText(products.get(position).code);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHoler extends RecyclerView.ViewHolder {

        TextView name,code,minqty,avlqty;

        public MyViewHoler(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lblName);
            code = itemView.findViewById(R.id.lblCode);
            minqty = itemView.findViewById(R.id.lblMinQty);
            avlqty = itemView.findViewById(R.id.lblAlvQty);
        }
    }
}
