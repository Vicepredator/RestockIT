package com.vicepredator.restockit;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import java.io.IOException;

/*
* Barcode reader by Vicepredator11
*
* to use this library follow theese steps:
* -Add the following lines to AndroidManifest.xml file:
*       <meta-data android:name="com.google.android.gms.vision.DEPENDENCIES" android:value="barcode"/>
*       <uses-permission android:name="android.permission.CAMERA" />
* -Add the following line to build.gradle file:
*       implementation 'com.google.android.gms:play-services-vision:10.0.0'
* -Create an object in you activity the following way:
*       BarcodeReader yourBarcodeReader = new BarcodeReader(yourActivity.this, yourSurfaceView);
* -To use the reader you need to create an event listenere this way:
*       yourBarcodeReader.setBarcodeReaderListener(new BarcodeReader.BarcodeReaderListener() {
*            @Override
*            public void onCodeScanned(String yourReadedBarcode) {
*                //Your code here
*                //yourReadedBarcode contains the value of the barcode you have read
*            }
*        });
*/

public class BarcodeReader {
    private static final String NEEDED_PERMISSION = "android.permission.CAMERA";
    private static final int REQUEST_ID = 1;
    private final BarcodeDetector detector;
    private final SurfaceView surfaceView;
    private CameraSource cameraSource;
    private final Context context;
    private final Activity activity;
    private String lastCode = "";

    private BarcodeReaderListener listener;

    public interface BarcodeReaderListener{
        void onCodeScanned(String code);
    }

    public BarcodeReader(Activity act, SurfaceView surface) {
        context = act.getApplicationContext();
        surfaceView = surface;
        activity = act;
        this.listener = null;
        detector = new BarcodeDetector.Builder(context).setBarcodeFormats(Barcode.QR_CODE | Barcode.EAN_13).build();
        if (!detector.isOperational()) {
            Toast.makeText(context, "Unable to activate the barcode reader", Toast.LENGTH_LONG).show();
            return;
        }

        cameraSource = new CameraSource
                .Builder(context, detector)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                activateCamera();
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
    }

    public void setBarcodeReaderListener(BarcodeReaderListener listener){
        this.listener = listener;
    }

    public void scan(){
        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                String thisCode;
                final SparseArray<Barcode> items = detections.getDetectedItems();
                if (items.size() != 0){
                    thisCode = items.valueAt(0).rawValue;
                    if(!thisCode.equalsIgnoreCase(lastCode)) {
                        lastCode = thisCode;
                        if (listener != null)
                            listener.onCodeScanned(items.valueAt(0).rawValue);
                    }
                }
            }

        });
    }

    private void activateCamera() {
        if (ActivityCompat.checkSelfPermission(context, NEEDED_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, NEEDED_PERMISSION)){
                ActivityCompat.requestPermissions(activity, new String[]{NEEDED_PERMISSION},REQUEST_ID);
            }
        } else {
            try {
                cameraSource.start(surfaceView.getHolder());
            } catch (IOException e) {
                Toast.makeText(context,"Errore while starting camera",Toast.LENGTH_LONG).show();
            }
        }
    }


}
