package com.example.a1dordj54.findapub.webservices.async;

import android.os.AsyncTask;

import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.views.fragmentInterfaces.MapFragmentView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AsyncGetPubsFromWebService extends AsyncTask<String,Void,String> {

    private MapFragmentView fragment;

    public AsyncGetPubsFromWebService(MapFragmentView callback) {
        this.fragment = callback;
    }

    @Override
    public String doInBackground(String... string) {
        HttpURLConnection conn = null;

        try {
            conn = (HttpURLConnection) new URL(string[0]).openConnection();

            InputStream in = conn.getInputStream();

            if (conn.getResponseCode() == 200) {

                String result = "", line;

                while ((line = new BufferedReader(new InputStreamReader(in)).readLine()) != null) {

                    result = result + line;
                }
                return result;
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    @Override
    public void onPostExecute(String result) {

        if (result != null) {

            ArrayList<Pub> pois = Pub.fromJSONToArray(result, fragment);

            this.fragment.getMap().addMarkers(pois);

            this.fragment.displaySnackbar("Add Pubs From Web");
        }
    }
}
