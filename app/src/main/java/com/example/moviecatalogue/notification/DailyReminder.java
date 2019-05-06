package com.example.moviecatalogue.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import com.example.moviecatalogue.HomeActivity;

public class DailyReminder extends BroadcastReceiver {

    public static final int NOTIFICATION_ID = 1;
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "movie channel";

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    private void notifDaily(Context context, String title, String message, int notifId) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(NOTIFICATION_ID, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultNotif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }
}
