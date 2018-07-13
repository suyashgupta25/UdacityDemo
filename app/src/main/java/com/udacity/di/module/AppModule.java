package com.udacity.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.udacity.data.AppDataManager;
import com.udacity.data.DataManager;
import com.udacity.data.database.courses.AppDatabaseHelper;
import com.udacity.data.database.courses.DatabaseHelper;
import com.udacity.data.processing.ApiProcesser;
import com.udacity.data.processing.AppApiProcessor;
import com.udacity.data.remote.ApiHelper;
import com.udacity.data.remote.AppApiHelper;
import com.udacity.di.ApiInfo;
import com.udacity.utils.AppConstants;
import com.udacity.utils.rx.AppSchedulerProvider;
import com.udacity.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by suyashg on 05/07/18.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiProcesser provideApiProcessor(AppApiProcessor appApiProcessor) {
        return appApiProcessor;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DatabaseHelper provideDataBaseHelper(AppDatabaseHelper appDatabaseHelper) {
        return appDatabaseHelper;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return AppConstants.PARAM_VALUE_CONTENT_TYPE;
    }
}
