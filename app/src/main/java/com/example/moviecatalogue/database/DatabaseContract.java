package com.example.moviecatalogue.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

//    public static String TABLE_MOVIE = "movie";
//    public static final class MovieColumns implements BaseColumns {
//        public static final String MOVIE_ID = "movie_id";
//        public static final String TITLE = "title";
//        public static final String OVERVIEW = "overview";
//        public static final String POSTER = "poster";
//    }
//    public static final String AUTHORITY = "com.example.moviecatalogue";
//
//    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
//            .authority(AUTHORITY)
//            .appendPath(TABLE_MOVIE)
//            .build();
//
//    public static String getColumnString(Cursor cursor, String columnName) {
//        return cursor.getString(cursor.getColumnIndex(columnName));
//    }
//
//    public static int getColumnInt(Cursor cursor, String columnName) {
//        return cursor.getInt(cursor.getColumnIndex(columnName));
//    }
//
//    public static long getColumnLong(Cursor cursor, String columnName) {
//        return cursor.getLong(cursor.getColumnIndex(columnName));
//    }
//
//    public static double getColumnDouble(Cursor cursor, String columnName) {
//        return cursor.getDouble(cursor.getColumnIndexOrThrow(columnName));
//    }

    public static final class MovieColumns implements BaseColumns {


        public static final String TABLE_MOVIE = "movie";
        public static String MOVIE_ID = "movie_id";
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String POSTER = "poster";
    }

    public static final String AUTHORITY = "com.example.moviecatalogue";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(MovieColumns.TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
