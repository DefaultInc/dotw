package com.example.amze.myapplication;

import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.amze.myapplication.tools.HttpClient;
import com.example.amze.myapplication.tools.MyServer;
import com.example.amze.myapplication.tools.WifiProvider;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    ConnectivityManager cman;

    MyServer myServer = MyServer.getInstance();
    ListView listView = null;
    Intent intent;
    String ssid = null;
    String myIp = null;
    String hostIp = null;
    List<String> messages = new ArrayList<String>();

    Button send;
    EditText msg;
    EditText userName;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.send = findViewById(R.id.button1);
        this.msg = findViewById(R.id.msg);
        this.userName = findViewById(R.id.userName);
        listView = findViewById(R.id.message_list);


        this.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(hostIp == null ) {
                    System.out.println(MyServer.users.size());
//                    HttpClient.send(MyServer.users., msg.getText().toString(), "send", userName.getText().toString() );
                    HttpClient.broadcast(MyServer.users, msg.getText().toString(), userName.getText().toString() );
                } else {
                    System.out.println(hostIp);
                    System.out.println(msg.getText().toString());
                    System.out.println(userName.getText().toString());

                    HttpClient.send(hostIp, msg.getText().toString(), "send", userName.getText().toString(), myIp);

                }

                messages.add(userName.getText().toString() + ": " + msg.getText().toString());
                ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_message_sender, R.id.text_sender_body, messages);
                listView.setAdapter(itemsAdapter);
                listView.smoothScrollToPosition(itemsAdapter.getCount() -1);
                msg.setText("");

            }
        });
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        cman = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        intent = getIntent();
//        myServer.startServer();

        MyServer.initiater.addListener(new MyServer.Responder() {
            @Override
            public void someoneSaidHello(String user, String msg) {
                //TODO
                Log.d("got message", user + msg);
                messages.add(user + ": " + msg);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_message_received, R.id.text_message_body, messages);
                        listView.setAdapter(itemsAdapter);
                        listView.smoothScrollToPosition(itemsAdapter.getCount() -1);
                    }
                });


            }
        });


        String pass = intent.getStringExtra("pass");
        if(intent.getStringExtra("ssid") != null && pass != null) {
            this.ssid = intent.getStringExtra("ssid").toString();
            WifiProvider.connectToWifi(ssid, pass, wifiManager);
            BroadcastReceiver receiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    myIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

//                    ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                    NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//                    if (mWifi.isConnected()) {
//
//                    }
                    String [] ip = myIp.split("\\.");
                    if(ip.length != 0 && !ip[0].equals("0")) {
                        ip[ip.length - 1] = "1";
                        hostIp = ip[0] + "." + ip[1] + "." + ip[2] + ".1";
                        Log.d("HOSTIP", hostIp);
                        Log.d("MyIP", myIp);
                        userName.setText(myIp);

//                        HttpClient.register( hostIp, myIp);
                        send.setEnabled(true);
                    }


                }
            };

            registerReceiver(receiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));
        } else {
            send.setEnabled(true);
        }

    }

    private NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


    }



}
