package com.example.moviecatalogue;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.moviecatalogue.notification.DailyReminder;
import com.example.moviecatalogue.notification.Preference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.sw_daily_reminder)
    Switch swDaily;

    @BindView(R.id.sw_release_reminder)
    Switch swRelease;

    private String DAILY_REMINDER = "daily";
    private String RELEASE_REMINDER = "release";
    private String KEY_DAILY_REMINDER = "dailyreminder";
    private String KEY_RELEASE_REMINDER = "releasereminder";

    public DailyReminder dailyReminder;
    public SharedPreferences dailyPref,releasePref;
    public SharedPreferences.Editor dailyEdt, releaseEdt;
    public Preference preference;

    private final String TAG = "Notification: ";


    String timeDaily = "08:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.settings);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dailyReminder = new DailyReminder();
        preference = new Preference(this);
        setPreference();

        swDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setDaily(isChecked);
            }
        });

        swRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setRelease(isChecked);
            }
        });
    }


    private void dailyOn() {
        String message = getResources().getString(R.string.message_daily_notif);
        preference.setTimeDaily(timeDaily);
        preference.setMsgDaily(message);
        Log.d(TAG, timeDaily);
        dailyReminder.setAlarm(SettingActivity.this, DAILY_REMINDER, timeDaily, message);
    }

    private void dailyOff() {
        dailyReminder.cancelNotif(SettingActivity.this);
    }

    private void setRelease(boolean isChecked) {
        releaseEdt = releasePref.edit();
        if (isChecked) {
            releaseEdt.putBoolean(KEY_RELEASE_REMINDER, true);
            releaseEdt.apply();
        } else {
            releaseEdt.putBoolean(KEY_RELEASE_REMINDER, false);
            releaseEdt.apply();
        }
    }

    private void setDaily(boolean isChecked) {
        dailyEdt = dailyPref.edit();
        if (isChecked) {
            dailyEdt.putBoolean(KEY_DAILY_REMINDER, true);
            dailyEdt.apply();
            dailyOn();
        } else {
            dailyEdt.putBoolean(KEY_DAILY_REMINDER, false);
            dailyEdt.apply();
            dailyOff();
        }
    }

    private void setPreference() {
        dailyPref = getSharedPreferences(DAILY_REMINDER, MODE_PRIVATE);
        boolean unCheckedDaily = dailyPref.getBoolean(KEY_DAILY_REMINDER, false);
        swDaily.setChecked(unCheckedDaily);
        releasePref = getSharedPreferences(RELEASE_REMINDER, MODE_PRIVATE);
        boolean unCheckedRelease = releasePref.getBoolean(KEY_RELEASE_REMINDER, false);
        swRelease.setChecked(unCheckedRelease);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
