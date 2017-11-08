package com.example.a1dordj54.findapub.models;

public class PubsTable {

    public static final String TABLE_NAME = "pubs";
    public static final String COLUMN_ID = "pubId";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_REGION = "region";
    public static final String COLUMN_LATITUDE = "lat";
    public static final String COLUMN_LONGITUDE = "long";
    public static final String COLUMN_DESCRIPTION = "description";

    public static final String[] ALL_COLS = {COLUMN_ID, COLUMN_NAME, COLUMN_TYPE,
            COLUMN_COUNTRY, COLUMN_REGION, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_DESCRIPTION};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_TYPE + " TEXT, " +
                    COLUMN_COUNTRY + " TEXT, " +
                    COLUMN_REGION + " TEXT, " +
                    COLUMN_LONGITUDE + " REAL, " +
                    COLUMN_LATITUDE + " REAL, " +
                    COLUMN_DESCRIPTION + " TEXT " +
                    ");";

    public static final String SQL_DROP = "DROP TABLE " + TABLE_NAME;
}
