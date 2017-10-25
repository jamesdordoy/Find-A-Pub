package com.example.a1dordj54.findapub.webservices;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 1dordj54 on 25/10/2017.
 */

public class PointsofInterestClient {

    public static final String USERNAME = "KingChickenWing";
    public static final String PASSWORD = "G0verness";

    private static final PointsofInterestClient ourInstance = new PointsofInterestClient();
    private PointOfInterestsClientActions client;

    public static PointsofInterestClient getInstance() {
        return ourInstance;
    }

    private PointsofInterestClient() {

        buildRetrofit();
    }

    private void buildRetrofit(){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(PointofInterestURLS.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create());

        Retrofit retrofit = builder.build();

        this.client = retrofit.create(PointOfInterestsClientActions.class);
    }

    public PointOfInterestsClientActions getClient(){
        return this.client;
    }

    public String getBasicAuthHeader(){

        String base = USERNAME + ":" + PASSWORD;

        String authHeader = "";
        try {
            authHeader = "Basic " + Base64.encodeToString(base.getBytes("UTF-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        return authHeader;
    }
}
