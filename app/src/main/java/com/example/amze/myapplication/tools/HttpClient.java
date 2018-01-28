package com.example.amze.myapplication.tools;

import android.os.AsyncTask;

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

        try {
            new RetrieveFeedTask().execute(("http://" + link + ":8080?action=register&ip=" + ip) );

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }


    public static void send(String link, String msg, String action, String userName ) {

        try {
            new RetrieveFeedTask().execute("http://" + link + ":8080?action=" + action + "&msg=" + msg + "&userName=" + userName);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }

    public static void broadcast(List<String> list, String msg, String userName){
        for (String ip: list) {
            send(ip, msg, "bcast", userName );
        }
    }

    static class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String... urls) {
            try {

                URL reqUrl = new URL(urls[0]);
                HttpURLConnection request = (HttpURLConnection) (reqUrl.openConnection());
                request.setRequestMethod("GET");
                request.connect();
                System.out.println(request.getResponseMessage());


            } catch (Exception e) {
                this.exception = e;

                return null;
            }
            return null;
        }

    }

}
