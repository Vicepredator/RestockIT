package com.vicepredator.restockit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.vicepredator.restockit.database.Product;
import com.vicepredator.restockit.database.ProductsDao;
import com.vicepredator.restockit.database.dbHandler;

public class AddRemove extends AppCompatActivity {

    int dialogQty = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove);
        Intent i = getIntent();
        dbHandler db = dbHandler.getDatabase(this);
        ProductsDao productDB = db.ProductsDao();
        Product p = productDB.getProductByCode(i.getStringExtra("barcode"));
        TextView prodName = (TextView) findViewById(R.id.lblProductName);
        TextView prodCode = (TextView) findViewById(R.id.lblCode);
        TextView avlQty = (TextView) findViewById(R.id.lblTitleQty);
        prodCode.setText(p.code);
        prodName.setText(p.productName);
        avlQty.setText(p.avlQty+"");
        Button btnAdd = (Button) findViewById(R.id.btnBuy);
        Button btnRemove = (Button) findViewById(R.id.btnUse);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDialog(productDB,p,1);
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDialog(productDB,p,2);
            }
        });
    }

    private void pickerDialog(ProductsDao db,Product p, int t){
        final AlertDialog.Builder d = new AlertDialog.Builder(AddRemove.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
        d.setTitle("Quantity");
        d.setMessage("Select the quantity you want to add");
        d.setView(dialogView);
        final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

            }
        });
        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (t){
                    case 1:
                        dialogQty= numberPicker.getValue();
                        p.avlQty += dialogQty;
                        db.updateProduct(p);
                        Toast.makeText(AddRemove.this,"Added "+dialogQty+" products",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddRemove.this,MainActivity.class));
                        break;
                    case 2:
                        dialogQty= numberPicker.getValue();
                        if(dialogQty > p.avlQty){
                            Toast.makeText(AddRemove.this,"You cant remove "+dialogQty+" items",Toast.LENGTH_LONG).show();
                        }else {
                            p.avlQty -= dialogQty;
                            db.updateProduct(p);
                            Toast.makeText(AddRemove.this, "Removed " + dialogQty + " products", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AddRemove.this, MainActivity.class));
                        }
                        break;
                }

            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }
}