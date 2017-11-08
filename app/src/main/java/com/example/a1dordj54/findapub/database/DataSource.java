package com.example.a1dordj54.findapub.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.models.PubReviewsTable;
import com.example.a1dordj54.findapub.models.PubsTable;

import java.util.ArrayList;
import java.util.UUID;

public class DataSource {

    private Context context;
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    public DataSource(Context c){
        this.context = c;
        this.dbHelper = new DBHelper(c);
        this.database = this.dbHelper.getWritableDatabase();
    }

    public void open(){
        this.database = this.dbHelper.getWritableDatabase();
    }

    public void close(){
        this.dbHelper.close();
    }

    public Boolean isOpen(){
        return this.database.isOpen();
    }

    public Pub CreateItem(Pub pub){

        if(!this.database.isOpen()){
            open();
        }

        pub.setId(UUID.randomUUID().toString());

        ContentValues values = pub.toValues();

        this.database.insert(PubsTable.TABLE_NAME, null, values);

        return pub;
    }

    public Boolean updatePub(Pub pub){

        if(!this.database.isOpen()){
            open();
        }

        ContentValues values = new ContentValues(8);

        values.put(PubsTable.COLUMN_NAME, pub.getName());
        values.put(PubsTable.COLUMN_TYPE, pub.getType());
        values.put(PubsTable.COLUMN_COUNTRY, pub.getCountry());
        values.put(PubsTable.COLUMN_REGION, pub.getRegion());
        values.put(PubsTable.COLUMN_LONGITUDE, pub.getLon());
        values.put(PubsTable.COLUMN_LATITUDE, pub.getLat());
        values.put(PubsTable.COLUMN_DESCRIPTION, pub.getDescription());

        String[] whereArgs = new String[] { String.valueOf(pub.getId()) };

        int i = database.update(PubsTable.TABLE_NAME, values, PubsTable.COLUMN_ID + "=?", whereArgs);

        if(i > 0)
            return true;
        else
            return false;
    }

    public ArrayList<Pub> getAllPubs(){
        ArrayList<Pub> pubs = new ArrayList<>();

        Cursor cursor = this.database.query(PubsTable.TABLE_NAME, PubsTable.ALL_COLS, null, null, null, null, null);

        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(PubsTable.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(PubsTable.COLUMN_NAME));
            String type = cursor.getString(cursor.getColumnIndex(PubsTable.COLUMN_TYPE));
            String county = cursor.getString(cursor.getColumnIndex(PubsTable.COLUMN_COUNTRY));
            String region = cursor.getString(cursor.getColumnIndex(PubsTable.COLUMN_REGION));
            Double lat = cursor.getDouble(cursor.getColumnIndex(PubsTable.COLUMN_LATITUDE));
            Double lon = cursor.getDouble(cursor.getColumnIndex(PubsTable.COLUMN_LONGITUDE));
            String description = cursor.getString(cursor.getColumnIndex(PubsTable.COLUMN_DESCRIPTION));

            pubs.add(new Pub(id, name ,type , county, region, lat, lon, description ));
        }

        cursor.close();

        return pubs;
    }

    public Boolean removePub(String row){

        String[] whereArgs = new String[] { String.valueOf(row) };
        int i = database.delete(PubsTable.TABLE_NAME, PubsTable.COLUMN_ID + "=?", whereArgs);

        if(i > 0){
            return true;
        }else{
            return false;
        }
    }


    public ArrayList<Pub> getPubReviews(){
        ArrayList<Pub> pubs = new ArrayList<>();

        Cursor cursor = this.database.query(PubsTable.TABLE_NAME, PubsTable.ALL_COLS, null, null, null, null, null);

        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(PubsTable.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(PubsTable.COLUMN_NAME));
            String type = cursor.getString(cursor.getColumnIndex(PubsTable.COLUMN_TYPE));
            String county = cursor.getString(cursor.getColumnIndex(PubsTable.COLUMN_COUNTRY));
            String region = cursor.getString(cursor.getColumnIndex(PubsTable.COLUMN_REGION));
            Double lat = cursor.getDouble(cursor.getColumnIndex(PubsTable.COLUMN_LATITUDE));
            Double lon = cursor.getDouble(cursor.getColumnIndex(PubsTable.COLUMN_LONGITUDE));
            String description = cursor.getString(cursor.getColumnIndex(PubsTable.COLUMN_DESCRIPTION));

            pubs.add(new Pub(id, name ,type , county, region, lat, lon, description ));
        }

        cursor.close();

        return pubs;
    }

    public void removePubReview(String row){

        String[] whereArgs = new String[] { String.valueOf(row) };
        database.delete(PubReviewsTable.TABLE_NAME, PubReviewsTable.COLUMN_ID + "=?", whereArgs);
    }

    public long getDataItemsCount(){
        return DatabaseUtils.queryNumEntries(this.database, PubsTable.TABLE_NAME);
    }
}

