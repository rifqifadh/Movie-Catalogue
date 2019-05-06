package com.example.moviecatalogue.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.entity.MovieItem;
import com.example.moviecatalogue.network.ApiClient;


import java.util.concurrent.ExecutionException;

import static com.example.moviecatalogue.database.DatabaseContract.CONTENT_URI;
import static com.example.moviecatalogue.widget.FavoriteWidget.EXTRA_ITEM;

public class StackRemoteView implements RemoteViewsService.RemoteViewsFactory {


    private Context context;
    private Cursor list;
    private int mAppWidgetId;
    private final String TAG = "FavoriteMovieAdapter";


    public StackRemoteView(Context applicationContext, Intent intent) {
        context = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

        list = context.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        MovieItem movieItem = getItem(position);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        Log.d(TAG, "STACK REMOTE VIEW: " + movieItem.getPosterPath());

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context).asBitmap().load(ApiClient.IMAGE_BASE_URL + movieItem.getPosterPath()).into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        remoteViews.setImageViewBitmap(R.id.image_widget, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.image_widget, fillIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private MovieItem getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItem(list);
    }
}
