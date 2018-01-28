package com.example.amze.myapplication.tools;

import java.util.List;

/**
 * Created by Amze on 28-Jan-18.
 */

public class HttpClient {

    public static void send(String url, String msg) {

    }

    public static void broadcast(List<User> list, String msg){
        for (User user: list) {
            send("http://" + user.ip + ":8080", msg );
        }
    }
}
