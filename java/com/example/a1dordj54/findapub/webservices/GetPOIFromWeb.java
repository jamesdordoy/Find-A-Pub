package com.example.a1dordj54.findapub.webservices;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.models.OpenStreetMaps.interfaces.Mapable;
import com.example.a1dordj54.findapub.models.PointofInterest;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.views.fragments.MapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPOIFromWeb extends AsyncTask<String,Void,String> {

    private Mapable map;

    public GetPOIFromWeb(Mapable callback) {
        this.map = callback;
    }


    @Override
    public String doInBackground(String... string){
        HttpURLConnection conn = null;

        try{
            conn = (HttpURLConnection) new URL(string[0]).openConnection();

            InputStream in = conn.getInputStream();

            if(conn.getResponseCode() == 200){

                String result = "", line;

                while((line = new BufferedReader(new InputStreamReader(in)).readLine()) != null) {

                    result = result + line;
                }
                return result;
            }else {
                return null;
            }
        }
        catch(IOException e){
            return null;
        }
        finally{
            if(conn!=null)
                conn.disconnect();
        }
    }

    @Override
    public void onPostExecute(String result) {

        try {

            JSONArray jArray = new JSONArray(result);

            for(int i=0; i < jArray.length() ; i++) {

                JSONObject jObject = jArray.getJSONObject(i);

                if(jObject != null){

                    PointofInterest poi = new Pub(jObject.getString("id"), jObject.getString("name"),
                                                jObject.getString("type"), jObject.getString("country"),
                                                jObject.getString("region"), jObject.getDouble("lat"),
                                                jObject.getDouble("lon"), jObject.getString("description"));

                    Drawable newMarker = (this.map.getView()).getResources().getDrawable(R.drawable.ic_web_marker);
                    poi.setMarker(newMarker);

                    this.map.addMarker(poi);
                }
            }

        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        }
    }
}

