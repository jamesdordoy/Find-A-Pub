package com.example.a1dordj54.findapub.models;

import android.content.ContentValues;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.views.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Pub extends PointofInterest implements Parcelable {

    public static final String NEW_PUB = "pub";

    private double rating;

    private Source source;
    private ArrayList<PubReview> reviews = new ArrayList<>();

    public Pub(String id, String name, String type, String country, String region, double lat, double lon, String description){
        super(id, name, type, country, region, lat, lon, description);
        this.rating = 0;

        source = Source.local;
    }

    public Pub(String id, String name, String type, String country, String region, double lat, double lon, String description, double rating, Source source){
        super(id, name, type, country, region, lat, lon, description);
        this.rating = rating;
        this.source = source;
    }

    public void reverseLatLon(){
        double lat = this.lat;
        double lon = this.lon;
        this.lat = lon;

        this.lon = lat;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String toPostData(){

        if(!this.id.equals("")){
            return "name=" + this.name + "&type=pub&country=" + this.country + "&region="
                    + this.region + "&lat" + Double.toString(this.lat) +  "&lon" + Double.toString(this.lon)
                    + "&description=" + this.description
                    +"&fromAndroid=1&androidKey=" + this.id;
        }
        /*return "name=" + this.name + "&type=pub&country=" + this.country + "&region="
                + this.region + "&lat" + Double.toString(this.lat) +  "&lon" + Double.toString(this.lon)
                + "&description=" + this.description
                +"&fromAndroid=1&androidKey=" + this.id;*/
        return "";
    }

    public ContentValues toValues(){
        ContentValues values = new ContentValues(8);

        values.put(PubsTable.COLUMN_ID, this.id);
        values.put(PubsTable.COLUMN_NAME, this.name);
        values.put(PubsTable.COLUMN_TYPE, this.type);
        values.put(PubsTable.COLUMN_COUNTRY, this.country);
        values.put(PubsTable.COLUMN_REGION, this.region);
        values.put(PubsTable.COLUMN_LONGITUDE, this.lon);
        values.put(PubsTable.COLUMN_LATITUDE, this.lat);
        values.put(PubsTable.COLUMN_DESCRIPTION, this.description);

        return values;
    }


    //Parcelable Code
    /** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<PointofInterest> CREATOR = new Parcelable.Creator<PointofInterest>() {

        public Pub createFromParcel(Parcel pc) { return new Pub(pc); }
        public Pub[] newArray(int size) { return new Pub[size]; }

    };

    //Parcelable Constructor
    public Pub(Parcel pc){
        super("", "", "", "","", 0,0,"");
        this.id = pc.readString();
        this.name = pc.readString();
        this.type = pc.readString();
        this.country = pc.readString();
        this.region = pc.readString();
        this.lon = pc.readDouble();
        this.lat = pc.readDouble();
        this.description = pc.readString();
        this.rating = pc.readDouble();
    }

    // Parcelable Methods
    @Override
    public int describeContents(){ return 0; }

    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeString(this.id);
        pc.writeString(this.name);
        pc.writeString(this.type);
        pc.writeString(this.country);
        pc.writeString(this.region);
        pc.writeDouble(this.lat);
        pc.writeDouble(this.lon);
        pc.writeString(this.description);
        pc.writeDouble(this.rating);
    }


    public static ArrayList<Pub> fromJSONToArray(String json, View view){

        if (!json.isEmpty()) {

            JSONArray jArray;

            try {
                jArray = new JSONArray(json);

                ArrayList<Pub> pois = new ArrayList<>();

                Pub poi;

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    if (jObject != null) {

                        double rating;


                        if (!jObject.isNull("rating")) {
                            rating = jObject.getDouble("rating");

                            poi = new Pub(jObject.getString("id"), jObject.getString("name"),
                                    jObject.getString("type"), jObject.getString("country"),
                                    jObject.getString("region"), jObject.getDouble("lat"),
                                    jObject.getDouble("lon"), jObject.getString("description"),
                                    rating, Source.network);
                        } else {
                            poi = new Pub(jObject.getString("id"), jObject.getString("name"),
                                    jObject.getString("type"), jObject.getString("country"),
                                    jObject.getString("region"), jObject.getDouble("lat"),
                                    jObject.getDouble("lon"), jObject.getString("description"),
                                    0, Source.network);
                        }

                        Drawable newMarker = (view.getLayout()).getResources().getDrawable(R.drawable.ic_web_marker);
                        poi.setMarker(newMarker);

                        pois.add(poi);
                    }
                }

                return pois;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}