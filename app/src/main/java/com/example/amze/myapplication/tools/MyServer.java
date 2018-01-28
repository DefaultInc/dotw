package com.example.amze.myapplication.tools;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Amze on 27-Jan-18.
 */

public class MyServer extends NanoHTTPD {
    private final static int PORT = 8080;
    private static MyServer instance = null;
    public static List<String> users = new ArrayList<String>();
    public static Initiater initiater = null;


    public static MyServer getInstance() {
        if(instance == null)
            try {
                instance = new MyServer();
                    initiater = new MyServer.Initiater();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return instance;
    }


//    public void stopServer(){
//        return stopServer();
//    }

    private MyServer() throws IOException {
        super(PORT);
        start();
        System.out.println( "\nRunning! Point your browers to http://localhost:8080/ \n" );
    }

    @Override
    public Response serve(IHTTPSession session) {
        String action = session.getParms().get("action");
        if(action != null) {
            if(action.equals("register") ) {

                String ip = session.getParms().get("ip");
                Log.d("register", ip);
                users.add(ip);

            } else if(action.equals("send")) {
                String msg = session.getParms().get("msg");
                String userName = session.getParms().get("userName");

                if(msg != null) {
                    HttpClient.broadcast(users, msg, userName);
                    initiater.sayHello(userName, msg);
                }
            } else if(action.equals("bcast")) {
                String msg = session.getParms().get("msg");
                String userName = session.getParms().get("userName");
                if(msg != null) {

                    initiater.sayHello(userName, msg);
                }

            }
        }

        return new NanoHTTPD.Response(Response.Status.OK, "application/json", "{\"status\":\"fail\"}");
    }


    //I dont have any time !!!!!!

    interface HelloListener {
        void someoneSaidHello(String user, String msg);
    }


    public static class Initiater {
        private List<HelloListener> listeners = new ArrayList<HelloListener>();

        public void addListener(HelloListener toAdd) {
            listeners.add(toAdd);
        }

        public void sayHello(String user, String msg) {
            System.out.println("Hello!!");

            // Notify everybody that may be interested.
            for (HelloListener hl : listeners)
                hl.someoneSaidHello(user, msg);
        }
    }



    // Someone interested in "Hello" events
    public static  class Responder implements HelloListener {
        @Override
        public void someoneSaidHello(String user, String msg) {
            System.out.println("Hello there...");
        }
    }



}