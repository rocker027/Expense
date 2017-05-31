package com.litto.expense;

import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        final String remindersKey = "reminders";
        boolean enabled = sharedPreferences.getBoolean(remindersKey, false);
        JobScheduler jobScheduler = (JobScheduler) getSystemService(
                JOB_SCHEDULER_SERVICE);
        if (!enabled){
            jobScheduler.cancel(300);
        }else{
            JobInfo job = new JobInfo.Builder(300,
                    new ComponentName(getPackageName(),
                            ScheduledNotificationJobService.class.getName()))
                    .setPersisted(true)
                    .setPeriodic(AlarmManager.INTERVAL_DAY)
                    .build();
            jobScheduler.schedule(job);
        }

//        JobInfo job = new JobInfo.Builder()
    }
}
