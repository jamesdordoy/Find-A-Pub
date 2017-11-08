package com.example.a1dordj54.findapub.models;

/**
 * Created by Dordoy on 06/11/2017.
 */

public class PubReviewsTable {

    public static final String TABLE_NAME = "pub_reviews";
    public static final String COLUMN_ID = "review_id";
    public static final String COLUMN_REVIEW = "review";
    public static final String COLUMN_RATING = "rating";


    public static final String[] ALL_COLS = {COLUMN_ID, COLUMN_REVIEW, COLUMN_RATING};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_REVIEW + " TEXT, " +
                    COLUMN_RATING + " REAL, " +
                    ");";

    public static final String SQL_DROP = "DROP TABLE " + TABLE_NAME;
}
