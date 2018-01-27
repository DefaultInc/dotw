package com.example.amze.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fi.iki.elonen.NanoHTTPD;

public class MainActivity extends AppCompatActivity {

    List<ScanResult> wifis = new ArrayList<ScanResult>();

    WifiManager mWifiManager;

    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                List<ScanResult> mScanResults = mWifiManager.getScanResults();
//                for (ScanResult wifi: mScanResults) {
//                    if()
//                }

                Log.d("wifi", mScanResults.size() + "");
            }
        }
    };

    Button button;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0x12345);




        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                try {
//                    MyServer server = new MyServer();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }



            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0x12345) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            registerReceiver(mWifiScanReceiver,
                    new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            mWifiManager.startScan();
        }
    }





}


