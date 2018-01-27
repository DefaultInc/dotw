package com.example.amze.myapplication;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.amze.myapplication.tools.MyServer;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        MyServer.getInstance();
        Intent intent = getIntent();
        Log.d("a", intent.toString());
    }

}
