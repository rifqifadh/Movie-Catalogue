package com.example.moviecatalogue.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moviecatalogue.model.MovieItem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.moviecatalogue.database.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.example.moviecatalogue.database.DatabaseContract.MovieColumns.TABLE_MOVIE;

public class MovieHelper {

    private static String DATABASE_TABLE = TABLE_MOVIE;



    private Context context;
    private DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;
    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
//        databaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

//    public ArrayList<MovieItem> getAllMoviesFav() {
//        ArrayList<MovieItem> arrayList = new ArrayList<>();
//        Cursor cursor = database.query(DATABASE_TABLE, null,
//                null,
//                null,
//                null,
//                null,
//                _ID + " ASC",
//                null);
//        cursor.moveToFirst();
//        MovieItem movieItem;
//        if (cursor.getCount() >  0) {
//            do {
//                movieItem = new MovieItem();
//                movieItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
//            } while (!cursor.isAfterLast());
//        }
//        cursor.close();
//        return arrayList;
//    }

    public MovieHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen()) databaseHelper.close();
    }

    public Cursor queryProvider() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC"
        );
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                MOVIE_ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }

    public long insertProvider(ContentValues values) {
        return database.insert(
                DATABASE_TABLE,
                null,
                values
        );
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(
                DATABASE_TABLE,
                values,
                MOVIE_ID + " = ?",
                new String[]{id}
        );
    }

    public int deleteProvider(String id) {
        return database.delete(
                DATABASE_TABLE,
                MOVIE_ID + " = ?",
                new String[]{id}
        );
    }
}
