package com.example.amze.myapplication.tools;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Amze on 27-Jan-18.
 */

public class MyServer extends NanoHTTPD {
    private final static int PORT = 8080;
    private static MyServer instance = null;

    public static MyServer getInstance() {
        if(instance == null)
            try {
                instance = new MyServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return instance;
    }

    public Boolean startServer(){
        return startServer();
    }
    public Boolean stopServer(){
        return stopServer();
    }

    private MyServer() throws IOException {
        super(PORT);
        System.out.println( "\nRunning! Point your browers to http://localhost:8080/ \n" );
    }

    @Override
    public Response serve(IHTTPSession session) {
        return new NanoHTTPD.Response(Response.Status.OK, "application/json", "{\"status\":\"norm\"}");
    }


}