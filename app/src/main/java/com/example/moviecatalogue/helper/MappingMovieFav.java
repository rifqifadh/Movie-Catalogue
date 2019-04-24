package com.example.moviecatalogue.helper;

import android.database.Cursor;

import com.example.moviecatalogue.model.MovieItem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.moviecatalogue.database.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.example.moviecatalogue.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.example.moviecatalogue.database.DatabaseContract.MovieColumns.POSTER;
import static com.example.moviecatalogue.database.DatabaseContract.MovieColumns.TITLE;

public class MappingMovieFav {

    public static ArrayList<MovieItem> mapCursorToArrayList(Cursor cursor) {

        ArrayList<MovieItem> movieItemsFav = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            int movieId = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
            movieItemsFav.add(new MovieItem(movieId,title,overview,poster));
        }

        return movieItemsFav;
    }
}
