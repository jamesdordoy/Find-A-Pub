package com.example.a1dordj54.findapub.webservices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 1dordj54 on 11/10/2017.
 */

public interface WebServiceCall {

    String BASE_URL = "http://192.168.0.12/";

    String PUB_URL = "api/PointOfInterest.php?type=pub";

    Retrofit retroFit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    
}
