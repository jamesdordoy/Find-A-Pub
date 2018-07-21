package com.example.a1dordj54.findapub.models;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Pub extends PointofInterest implements Parcelable {

    private int rating;

    public Pub(String id, String name, String type, String country, String region, double lon, double lat, String description){
        super(id, name, type, country, region, lon, lat, description);
        this.rating = 0;
    }

    /**Ctor from Parcel, reads back fields IN THE ORDER they were written */
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
        this.rating = pc.readInt();
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
        pc.writeInt(this.rating);
    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<PointofInterest> CREATOR = new Parcelable.Creator<PointofInterest>() {

        public Pub createFromParcel(Parcel pc) { return new Pub(pc); }
        public Pub[] newArray(int size) { return new Pub[size]; }

    };

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}