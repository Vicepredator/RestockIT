package com.vicepredator.restockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vicepredator.restockit.database.*;

public class NewProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        final TextView minQty =  findViewById(R.id.txtMinQty);
        TextView productCode = findViewById(R.id.txtProductCode);
        TextView qty = findViewById(R.id.txtQty);
        TextView productName = findViewById(R.id.txtProductName);
        Button btninsert = findViewById(R.id.btnFinish);
        Intent input = getIntent();
        dbHandler db = dbHandler.getDatabase(this);
        ProductsDao productDB = db.ProductsDao();
        if(input.hasExtra("barcode")){
            productCode.setText(input.getStringExtra("barcode"));
        }
        btninsert.setOnClickListener(v -> {
            if(productCode.getText().toString().isEmpty()){
                Toast.makeText(NewProduct.this, "You have to insert a code",Toast.LENGTH_LONG).show();
            }else if(Integer.parseInt(qty.getText().toString()) <= 0){
                Toast.makeText(NewProduct.this, "Quantity can't be less than 0",Toast.LENGTH_LONG).show();
            }else if(productName.getText().toString().isEmpty()){
                Toast.makeText(NewProduct.this, "You have to insert a name",Toast.LENGTH_LONG).show();
            }else{
                Product p = new Product();
                p.code = productCode.getText().toString();
                p.productName = productName.getText().toString();
                p.minQty = Integer.parseInt(minQty.getText().toString());
                p.avlQty = Integer.parseInt(qty.getText().toString());
                Log.d("Salve",Integer.parseInt(minQty.getText().toString())+"");
                Log.d("Salvini",Integer.parseInt(qty.getText().toString())+"");
                productDB.insertProduct(p);
                Toast.makeText(NewProduct.this, "You have inserted new product: "+p.productName,Toast.LENGTH_LONG).show();
                startActivity(new Intent(NewProduct.this,MainActivity.class));
            }
        });
    }
}