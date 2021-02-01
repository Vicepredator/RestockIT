package com.vicepredator.restockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.vicepredator.restockit.database.*;

public class Scan extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        SurfaceView camPreview = (SurfaceView) findViewById(R.id.scannerPreview);
        BarcodeReader barRead = new BarcodeReader(Scan.this, camPreview);
        barRead.scan();
        dbHandler db = dbHandler.getDatabase(this);
        ProductsDao productDB = db.ProductsDao();

        barRead.setBarcodeReaderListener(new BarcodeReader.BarcodeReaderListener() {
            @Override
            public void onCodeScanned(String barcode) {
                Log.d("Vicecode",barcode+"");
                try{
                    Intent i;
                    Log.e("barcode", barcode+"");
                    Log.d("DBLog", "Query results: "+productDB.getProductFromCode(barcode));
                    if(productDB.getProductFromCode(barcode).isEmpty()){
                        i = new Intent(Scan.this, NewProduct.class);
                    }else{
                        i = new Intent(Scan.this,AddRemove.class);
                    }
                    i.putExtra("barcode",barcode);
                    startActivity(i);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}