package com.omeerfk.dizitakibi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */

public class NetworkHelper {

    public static boolean hasNetworkAccess(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {

            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null && info.isConnectedOrConnecting();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}