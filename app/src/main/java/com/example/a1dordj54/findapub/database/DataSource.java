package com.example.a1dordj54.findapub.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.models.PubsTable;

import java.util.ArrayList;

public class DataSource {

    private Context context;
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    public DataSource(Context c){
        this.context = c;
        dbHelper = new DBHelper(c);
        database = dbHelper.getWritableDatabase();
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Pub CreateItem(Pub pub){

        ContentValues values = pub.toValues();

        database.insert(PubsTable.TABLE_PUBS, null, values);

        return pub;
    }

    public ArrayList<Pub> getAllPubs(){
        ArrayList<Pub> pubs = new ArrayList<>();

        Cursor cursor = this.database.query(PubsTable.TABLE_PUBS, PubsTable.ALL_COLS, null, null, null, null, null);

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

        return pubs;
    }

    public long getDataItemsCount(){
        return DatabaseUtils.queryNumEntries(this.database, PubsTable.TABLE_PUBS);
    }
}

