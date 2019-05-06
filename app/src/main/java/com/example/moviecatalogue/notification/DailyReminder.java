package com.example.moviecatalogue.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.moviecatalogue.HomeActivity;
import com.example.moviecatalogue.R;

import java.util.Calendar;
import java.util.Objects;

public class DailyReminder extends BroadcastReceiver {

    public static final int NOTIFICATION_ID = 10;
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "movie channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        notifDaily(context, context.getResources().getString(R.string.message_daily_notif),
                intent.getStringExtra("message"), NOTIFICATION_ID);
    }

    private void notifDaily(Context context, String title, String message, int notifId) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(NOTIFICATION_ID, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultNotif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle(title)
                .setContentText(message)
                .setVibrate(new long[] {1000, 1000, 1000, 1000})
                .setSound(defaultNotif);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (notificationManager != null) {
            notificationManager.notify(notifId, notification);
        }
    }

    public void cancelNotif(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        (alarmManager).cancel(pendingIntent);
    }

    public void setAlarm(Context context, String type, String time, String message) {
        cancelNotif(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);
        intent.putExtra("message", message);
        intent.putExtra("type", type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
//        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        Objects.requireNonNull(alarmManager).setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
