package com.penguin.meetapenguin.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.Result;
import com.penguin.meetapenguin.dblayout.ContactController;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.util.entitiesHelper.ContactHelper;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * This a activity that allows the user to scan a QR Code while receiving a sharing contact.
 */
public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "ScanActivity";

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        Contact c = ContactHelper.fromJson(rawResult.getText());
        if (c != null) {
            Log.d(TAG, "contact: " + c.toString() + "|" + c.getName());

            ContactController controller = new ContactController(this);
            Log.d(TAG, "before: " + controller.readAll());
            controller.create(c);
            Log.d(TAG, "--------------------------");
            Log.d(TAG, "after: " + controller.readAll());
        }


        finish();

        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }
}
