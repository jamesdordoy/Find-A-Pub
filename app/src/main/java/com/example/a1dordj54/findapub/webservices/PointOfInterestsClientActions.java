package com.example.a1dordj54.findapub.webservices;

import com.example.a1dordj54.findapub.models.Pub;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PointOfInterestsClientActions {


    @GET(PointofInterestURLS.POINT_OF_INTERESTS_URL)
    Call<String> getPubs();

    @FormUrlEncoded
    @POST(PointofInterestURLS.ADD_POINT_OF_INTEREST_URL)
    Call<ResponseBody> createPub(@Field("name") String name,
                                 @Field("type") String type,
                                 @Field("country") String country,
                                 @Field("region") String region,
                                 @Field("lat") Double lat,
                                 @Field("lon") Double lon,
                                 @Field("description") String description,
                                 @Field("fromAndroid") int fromAndroid,
                                 @Field("AndroidKey") String androidId,
                                 @Header("Authorization") String authHeader);
}
