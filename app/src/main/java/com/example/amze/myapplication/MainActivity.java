package com.example.amze.myapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.amze.myapplication.tools.MyServer;
import com.example.amze.myapplication.tools.WifiProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> wifiNetworkList = new ArrayList<String>();
    private WifiManager wifiManager;
    ConnectivityManager cman;
    private MyServer serverInstance = MyServer.getInstance();
    private Boolean serverRunningState = false;
    ListView wifiList;


    Button button;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        wifiList = findViewById(R.id.ListView);
//        this.serverRunningState = this.serverInstance.startServer();
        cman = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0x12345);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                connectToWifi("smartpoint", "smartpoint");

                requestPermissions(new String[]{Manifest.permission.CHANGE_WIFI_STATE,Manifest.permission.WRITE_SETTINGS, Manifest.permission.ACCESS_WIFI_STATE}, 0x2);


//                WifiProvider.connectToWifi("smartpoint","smartpoint", wifiManager);
            }

        });

    }

    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
//                List<ScanResult> mScanResults = wifiManager.getScanResults();
                for (ScanResult result: (List<ScanResult>)wifiManager.getScanResults()) {
                    wifiNetworkList.add(result.SSID);
                }

//                for (ScanResult wifi: mScanResults) {
//                    if()
//                }
                if(wifiNetworkList != null && wifiNetworkList.size() != 0 ) {
                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, wifiNetworkList);
                    wifiList.setAdapter(itemsAdapter);
                }
                wifiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                        String ssid = (String) listView.getAdapter().getItem(position);
                        Intent intent = new Intent(listView.getContext(), ChatActivity.class);
                        intent.putExtra("name", ssid);
                        listView.getContext().startActivity(intent);
                    }
                });
                Log.d("number of wifi", wifiNetworkList.size() + "");

            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0x12345) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            registerReceiver(mWifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifiManager.startScan();
        } else if(requestCode == 0x2) {

            for (int grantResult : grantResults) {
                System.out.println(grantResult != PackageManager.PERMISSION_GRANTED );
            }
            WifiProvider.shareWifi("offchat_dauren", "secret", wifiManager, cman);
        }
    }



}