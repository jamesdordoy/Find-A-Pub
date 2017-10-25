package com.example.a1dordj54.findapub.webservices.async;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.presenters.fragmentInterfaces.MapFragmentView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

            JSONArray jArray = null;

            try {
                jArray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Pub> pois = new ArrayList<>();

            Pub poi;

            for (int i = 0; i < jArray.length(); i++) {

                JSONObject jObject = null;

                try {
                    jObject = jArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (jObject != null) {

                    double rating;

                    try {

                        if (!jObject.isNull("rating")) {
                            rating = jObject.getDouble("rating");

                            poi = new Pub(jObject.getString("id"), jObject.getString("name"),
                                    jObject.getString("type"), jObject.getString("country"),
                                    jObject.getString("region"), jObject.getDouble("lat"),
                                    jObject.getDouble("lon"), jObject.getString("description"),
                                    rating);
                        }else{
                            poi = new Pub(jObject.getString("id"), jObject.getString("name"),
                                    jObject.getString("type"), jObject.getString("country"),
                                    jObject.getString("region"), jObject.getDouble("lat"),
                                    jObject.getDouble("lon"), jObject.getString("description"),
                                    0);
                        }

                        Drawable newMarker = (this.fragment.getLayout()).getResources().getDrawable(R.drawable.ic_web_marker);
                        poi.setMarker(newMarker);



                        pois.add(poi);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            this.fragment.addMarkers(pois);

            this.fragment.displaySnackbar("Add Pubs From Web");
        }
    }
}
