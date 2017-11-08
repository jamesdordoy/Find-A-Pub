package com.example.a1dordj54.findapub.webservices.async;

import android.os.AsyncTask;
import android.util.Base64;

import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.views.fragmentInterfaces.MapFragmentView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 1dordj54 on 22/10/2017.
 */

public class AsyncAddPubToWebService extends AsyncTask<String,Void,String> {

    private MapFragmentView fragment;
    private Pub pub;

    public AsyncAddPubToWebService(MapFragmentView callback, Pub pub) {

        this.fragment = callback;
        this.pub = pub;
    }


    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection conn = null;

        try{
            conn = (HttpURLConnection) new URL(params[0]).openConnection();

            String username = "KingChickenWing";
            String password = "G0verness";

            String encoded = Base64.encodeToString((username + ":" + password).getBytes("UTF-8"), Base64.NO_WRAP);
            conn.setRequestProperty("Authorization", "Basic " + encoded);

            // For POST
            conn.setRequestMethod("POST");
            conn.setDoInput(true);

            String postData = this.pub.toPostData();

            conn.setFixedLengthStreamingMode(postData.length());

            OutputStream out = conn.getOutputStream();
            out.write(postData.getBytes());

            if(conn.getResponseCode() == 200) {

                return postData;
            }else{
                return "HTTP ERROR: " + conn.getResponseCode();
            }
        }
        catch(IOException e){
            return e.getMessage();
        }
        finally{
            if(conn!=null)
                conn.disconnect();
        }
    }


    @Override
    public void onPostExecute(String result) {

        this.fragment.displaySnackbar(result);
    }
}
