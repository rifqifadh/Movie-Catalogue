package com.example.moviecatalogue.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.moviecatalogue.database.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.example.moviecatalogue.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.example.moviecatalogue.database.DatabaseContract.MovieColumns.POSTER;
import static com.example.moviecatalogue.database.DatabaseContract.MovieColumns.TABLE_MOVIE;
import static com.example.moviecatalogue.database.DatabaseContract.MovieColumns.TITLE;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static int DATABASE_VERSION = 8;
//
    private static String DATABASE_NAME = "dbfavorite";
//
    public static final String CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL)",
            TABLE_MOVIE,
            _ID,
            MOVIE_ID,
            TITLE,
            OVERVIEW,
            POSTER
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }
}
