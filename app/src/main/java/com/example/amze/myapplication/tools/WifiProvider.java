package com.example.amze.myapplication.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Amze on 27-Jan-18.
 */

public class WifiProvider {

    public static void connectToWifi(String ssid, String pass, WifiManager wifiManager) {

        if(!wifiManager.isWifiEnabled())
        {
            wifiManager.setWifiEnabled(true);
        }
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", ssid);
        wifiConfig.preSharedKey = String.format("\"%s\"", pass);

        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();

    }

    public static Boolean shareWifi(String ssid, String pass, WifiManager wifiManager, ConnectivityManager cman) {

        Method[] methods = cman.getClass().getMethods();
        Boolean result = false;
        try
        {
            wifiManager.setWifiEnabled(false);
            Method enableWifi = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            // your Password
            WifiConfiguration  myConfig =  new WifiConfiguration();
            myConfig.SSID = ssid;
            myConfig.preSharedKey  = pass ;
            myConfig.status = WifiConfiguration.Status.ENABLED;
            myConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            myConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            myConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            myConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            myConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            myConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            myConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            result = (Boolean) enableWifi.invoke(wifiManager, myConfig, true);

        }
        catch (Exception e)
        {
            e.printStackTrace();
//            result = false;
        }
        return result;


//        if (wifiManager.isWifiEnabled()) {
//            wifiManager.setWifiEnabled(false);
//        }
//        Method[] wmMethods = wifiManager.getClass().getDeclaredMethods();
//        boolean methodFound = false;
//        for (Method method: wmMethods) {
//            if (method.getName().equals("setWifiApEnabled")) {
//                methodFound = true;
//                WifiConfiguration netConfig = new WifiConfiguration();
//                netConfig.SSID = "dataanywhere";
//                netConfig.preSharedKey="DataAnywhere";
//                netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
//                netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
//                try {
//                    boolean apstatus = (Boolean) method.invoke(wifiManager, netConfig, true);
//
//                    for (Method isWifiApEnabledmethod: wmMethods) {
//                        if (isWifiApEnabledmethod.getName().equals("isWifiApEnabled")) {
//                            while (!(Boolean) isWifiApEnabledmethod.invoke(wifiManager)) {};
//                            for (Method method1: wmMethods) {
//                                if (method1.getName().equals("getWifiApState")) {
//                                    int apstate;
//                                    apstate = (Integer) method1.invoke(wifiManager);
//                                    Log.i("wifi sharing", "Apstate ::: "+apstate);
//                                }
//                            }
//                        }
//                    }
//                    if (apstatus) {
//                        Log.d("Splash Activity", "Access Point created");
//                    } else {
//                        Log.d("Splash Activity", "Access Point creation failed");
//                    }
//
//                } catch (IllegalArgumentException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        if (!methodFound) {
//            Log.d("Splash Activity",
//                    "cannot configure an access point");
//        }
//        if(wifiManager.isWifiEnabled())
//        {
//            wifiManager.setWifiEnabled(false);
//        }
//        WifiConfiguration netConfig = new WifiConfiguration();
//        netConfig.SSID = ssid;
//        netConfig.preSharedKey = pass;
//        netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
//        netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
//        netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
//        netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//
//        try{
//            Method setWifiApMethod = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
//            boolean apstatus=(Boolean) setWifiApMethod.invoke(wifiManager, netConfig,true);
//
//            Method isWifiApEnabledmethod = wifiManager.getClass().getMethod("isWifiApEnabled");
//            while(!(Boolean)isWifiApEnabledmethod.invoke(wifiManager)){};
//            Method getWifiApStateMethod = wifiManager.getClass().getMethod("getWifiApState");
//            int apstate=(Integer)getWifiApStateMethod.invoke(wifiManager);
//            Method getWifiApConfigurationMethod = wifiManager.getClass().getMethod("getWifiApConfiguration");
//            netConfig=(WifiConfiguration)getWifiApConfigurationMethod.invoke(wifiManager);
//            Log.e("CLIENT", "\nSSID:"+netConfig.SSID+"\nPassword:"+netConfig.preSharedKey+"\n");
//
//        } catch (Exception e) {
//            Log.e("wifi sharing", "", e);
//        }
    }



}
