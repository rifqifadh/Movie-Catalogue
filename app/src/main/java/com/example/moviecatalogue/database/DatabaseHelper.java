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
import static com.example.moviecatalogue.database.DatabaseContract.TvColumns.OVERVIEW_TV;
import static com.example.moviecatalogue.database.DatabaseContract.TvColumns.POSTER_TV;
import static com.example.moviecatalogue.database.DatabaseContract.TvColumns.TABLE_TV;
import static com.example.moviecatalogue.database.DatabaseContract.TvColumns.TITLE_TV;
import static com.example.moviecatalogue.database.DatabaseContract.TvColumns.TV_ID;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static int DATABASE_VERSION = 10;

    private static String DATABASE_NAME = "dbfavorite";

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

    public static final String CREATE_TABLE_TV = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_TV,
            _ID,
            TV_ID,
            TITLE_TV,
            OVERVIEW_TV,
            POSTER_TV
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
        db.execSQL(CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TV);
        onCreate(db);
    }
}
