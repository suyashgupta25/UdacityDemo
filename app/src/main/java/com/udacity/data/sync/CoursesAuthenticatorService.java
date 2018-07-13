package com.udacity.data.sync;

/**
 * Created by suyashg on 08/07/18.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * The service which allows the sync adapter framework to access the mAuthenticator.
 */
public class CoursesAuthenticatorService extends Service {

    private CoursesAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        mAuthenticator = new CoursesAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
