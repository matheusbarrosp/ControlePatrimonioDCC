package com.example.matheus.projeto_final;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    private static int CAMERA_PERMISSION = 0;
    public static int RESULT_CAMERA_OK = 2;
    public static int RESULT_CAMERA_FAIL = 3;
    public static String RESULT_CAMERA_KEY = "RESULT_CAMERA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int grantResults[]) {
        if (requestCode == CAMERA_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setContentView(R.layout.activity_camera);
            initCam();
        }
    }

    private void cameraCallback(int resultCode, String result) {
        if(result != null){
            getIntent().putExtra(RESULT_CAMERA_KEY, result);
        }
        setResult(resultCode, getIntent());
        finish();
    }

    public void initCam() {
        final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector).build();
        final SurfaceView cameraView = (SurfaceView) findViewById(R.id.surfaceView);
        final CameraActivity cameraActivity = this;
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(cameraActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(cameraView.getHolder());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size() != 0){
                    String value = barcodes.valueAt(0).displayValue;
                    cameraActivity.cameraCallback(RESULT_CAMERA_OK, value);
                }
            }
        });

        if(!barcodeDetector.isOperational()){
            cameraCallback(RESULT_CAMERA_FAIL, null);
        }

    }
}
