package com.udacity.data.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.udacity.utils.AppLogger;

/**
 * Created by suyashg on 08/07/18.
 */

public class CoursesSyncService extends Service {

    public final String LOG_TAG = CoursesSyncService.class.getSimpleName();

    private static final Object sSyncAdapterLock = new Object();
    private static CoursesSyncAdapter coursesSyncAdapter = null;

    @Override
    public void onCreate() {
        super.onCreate();
        AppLogger.d(LOG_TAG, "onCreate - CoursesSyncService");
        synchronized (sSyncAdapterLock) {
            if (coursesSyncAdapter == null) {
                coursesSyncAdapter = new CoursesSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return coursesSyncAdapter.getSyncAdapterBinder();
    }
}
