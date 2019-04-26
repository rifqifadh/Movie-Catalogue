package com.example.moviecatalogue.provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.moviecatalogue.database.DatabaseContract;
import com.example.moviecatalogue.database.MovieHelper;
import com.example.moviecatalogue.database.TvHelper;

import java.util.Objects;

import static com.example.moviecatalogue.database.DatabaseContract.CONTENT_URI;
import static com.example.moviecatalogue.database.DatabaseContract.MovieColumns.TABLE_MOVIE;
import static com.example.moviecatalogue.database.DatabaseContract.TvColumns.TABLE_TV;

public class FavoriteProvider extends ContentProvider {

    private static final int FAVORITE_MOVIE = 1;
    private static final int FAVORITE_ID = 2;
    private static final int FAVORITE_TV = 3;
    private static final int FAVORITE_TV_ID = 4;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, TABLE_MOVIE, FAVORITE_MOVIE);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, TABLE_MOVIE + "/#", FAVORITE_ID);

        sUriMatcher.addURI(DatabaseContract.AUTHORITY, TABLE_TV, FAVORITE_TV);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, TABLE_TV + "/#", FAVORITE_TV_ID);
    }

    private MovieHelper movieHelper;
    private TvHelper tvHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        tvHelper = new TvHelper(getContext());
        movieHelper.open();
        tvHelper.open();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_MOVIE:
                cursor = movieHelper.queryProvider();
                break;

            case FAVORITE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;

            case FAVORITE_TV:
                cursor = tvHelper.queryProvider();
                break;

            case FAVORITE_TV_ID:
                cursor = tvHelper.queryByIdProvider(uri.getLastPathSegment());
                break;

            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_MOVIE:
                added = movieHelper.insertProvider(values);
                break;

            case FAVORITE_TV:
                added = tvHelper.insertProvider(values);
                break;

            default:
                added = 0;
                break;
        }

        if (added > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;

            case FAVORITE_TV_ID:
                deleted = tvHelper.deleteProvider(uri.getLastPathSegment());
                break;

            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(), values);
                break;

            case FAVORITE_TV_ID:
                updated = tvHelper.updateProvider(uri.getLastPathSegment(), values);

            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }

        return updated;
    }
}
