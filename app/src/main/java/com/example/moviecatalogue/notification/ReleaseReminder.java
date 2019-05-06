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
import android.util.Log;

import com.example.moviecatalogue.HomeActivity;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.entity.MovieItem;
import com.example.moviecatalogue.entity.MovieListResponse;
import com.example.moviecatalogue.network.ApiClient;
import com.example.moviecatalogue.network.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviecatalogue.network.ApiClient.API_KEY;

public class ReleaseReminder extends BroadcastReceiver {

    public static final int pageNo = 1;
    public static final int NOTIFICATION_ID = 11;
    public static String CHANNEL_ID = "channel_02";
    public static CharSequence CHANNEL_NAME = "movie channel";

    private final String TAG = "Release Reminder: ";


    @Override
    public void onReceive(Context context, Intent intent) {
        getMovies(context);
    }

    private void getMovies(final Context context) {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieListResponse> call = apiService.getMovieDsicover(API_KEY, pageNo);
        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                List<MovieItem> movieItems = response.body().getResults();

                String title = movieItems.get(0).getTitle();
                String getMessage = context.getResources().getString(R.string.message_release_today);
                String message = title + " " + getMessage;
                String releaseDate = movieItems.get(0).getReleaseDate();
                int id  = movieItems.get(0).getId();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();
                final String getTime = dateFormat.format(date);
                Log.d(TAG, "TIME: " + releaseDate);

                    if (releaseDate.equals(getTime)) {
                        notifRelease(context, title, message, id);
                    }
                }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {

            }
        });

    }

    private void notifRelease(Context context, String title, String message, int notifId) {

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
        mBuilder.setAutoCancel(true);

        if (notificationManager != null) {
            notificationManager.notify(notifId, notification);
        }
    }

    public void cancelNotifRelease(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        (alarmManager).cancel(pendingIntent);
    }

    public void setAlarmRelease(Context context, String type, String time, String message) {
        cancelNotifRelease(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);
        intent.putExtra("message", message);
        intent.putExtra("type", type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        (alarmManager).setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}
