package com.nuhkoca.udacitymoviesapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.nuhkoca.udacitymoviesapp.App;
import com.nuhkoca.udacitymoviesapp.BuildConfig;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by nuhkoca on 2/19/18.
 */

public class ConnectionSniffer {

    private static final int TIMEOUT_DURATION = 5;
    private static boolean isReachable;

    public static boolean sniff() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        new MyConnectionSniffer().execute(BuildConfig.BASEURL);

        return networkInfo != null &&
                networkInfo.isConnected() &&
                networkInfo.isConnectedOrConnecting() &&
                networkInfo.isAvailable() &&
                !isReachable;
    }


    private static class MyConnectionSniffer extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            String hostName = strings[0];

            try {
                return InetAddress.getByName(hostName).isReachable(TIMEOUT_DURATION);
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            isReachable = aBoolean;
        }
    }
}
