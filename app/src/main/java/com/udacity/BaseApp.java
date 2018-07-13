package com.udacity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.udacity.data.sync.CoursesSyncAdapter;
import com.udacity.di.component.DaggerAppComponent;
import com.udacity.utils.AppLogger;
import com.udacity.utils.glide.CircularTransformation;
import com.udacity.utils.glide.GlideBindingConfig;
import com.udacity.utils.glide.GlideUtils;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by suyashg on 05/07/18.
 */

public class BaseApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        AppLogger.init();

        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }
        GlideUtils.initConfig(this);

        CoursesSyncAdapter.initializeSyncAdapter(getApplicationContext());
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

}
