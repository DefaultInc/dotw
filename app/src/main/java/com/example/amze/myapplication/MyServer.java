package com.example.amze.myapplication;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Amze on 27-Jan-18.
 */

public class MyServer extends NanoHTTPD {
    private final static int PORT = 8080;

    public MyServer() throws IOException {
        super(PORT);
        start();
        System.out.println( "\nRunning! Point your browers to http://localhost:8080/ \n" );
    }

    @Override
    public Response serve(IHTTPSession session) {
        String msg = "<html><body><h1>Hello server</h1>\n";
        msg += "<p>We serve " + session.getUri() + " !</p>";
        return newFixedLengthResponse( msg + "</body></html>\n" );
    }

    private Response newFixedLengthResponse(String s) {
        return new Response("kuku");

    }
}