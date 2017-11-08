package com.example.a1dordj54.findapub.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a1dordj54.findapub.models.PubReviewsTable;
import com.example.a1dordj54.findapub.models.PubsTable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String CONNECTION_CLOSED = "Database Connection is Not Open";
    public static final String DB_FILE_NAME = "findapub.db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PubsTable.SQL_CREATE);
        db.execSQL(PubReviewsTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PubsTable.SQL_DROP);
        db.execSQL(PubReviewsTable.SQL_DROP);
        onCreate(db);
    }
}

