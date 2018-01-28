package com.example.amze.myapplication.tools;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Amze on 28-Jan-18.
 */

public class HttpClient {

    public static void register(String link, String ip) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(link + "?action=register&ip=" + ip);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
    }

    public static void send(String link, String msg, String action, String userName ) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(link + "?action=" + action + "&msg=" + msg + "&userName=" + userName);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

    }

    public static void broadcast(List<String> list, String msg, String userName){
        for (String ip: list) {
            send("http://" + ip + ":8080", msg, "bcast", userName );
        }
    }
}
