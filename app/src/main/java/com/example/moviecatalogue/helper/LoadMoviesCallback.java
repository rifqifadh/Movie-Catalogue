package com.example.moviecatalogue.helper;

import android.database.Cursor;

public interface LoadMoviesCallback {

    void preExecute();

    void postExecute(Cursor movies);
}
