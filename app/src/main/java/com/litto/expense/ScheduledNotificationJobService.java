package com.litto.expense;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by tom on 2016/11/21.
 */

public class ScheduledNotificationJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
