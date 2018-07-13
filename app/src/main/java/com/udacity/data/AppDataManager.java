package com.udacity.data;

import android.content.Context;
import android.support.v4.content.CursorLoader;

import com.google.gson.Gson;
import com.udacity.data.database.courses.AppDatabaseHelper;
import com.udacity.data.model.api.courses.Course;
import com.udacity.data.model.api.courses.CourseTemplate;
import com.udacity.data.model.api.courses.CoursesListResponse;
import com.udacity.data.processing.ApiProcesser;
import com.udacity.data.remote.ApiHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by suyashg on 05/07/18.
 */

public class AppDataManager implements DataManager {

    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final Gson mGson;

    private final ApiProcesser mApiProcesser;

    private final AppDatabaseHelper mAppDatabaseHelper;

    @Inject
    public AppDataManager(ApiHelper apiHelper, Context context, Gson gson, ApiProcesser apiProcesser, AppDatabaseHelper appDatabaseHelper) {
        this.mApiHelper = apiHelper;
        this.mContext = context;
        this.mGson = gson;
        this.mApiProcesser = apiProcesser;
        this.mAppDatabaseHelper = appDatabaseHelper;
    }

    @Override
    public Single<CoursesListResponse> getCourses() {
        return mApiHelper.getCourses();
    }

    @Override
    public List<CourseTemplate> processCoursesResponse(CoursesListResponse response) {
        return mApiProcesser.processCoursesResponse(response);
    }

    @Override
    public CursorLoader getCoursesListCursorLoader(Context context) {
        return mAppDatabaseHelper.getCoursesListCursorLoader(context);
    }

    @Override
    public CursorLoader getCourseDetailsCursorLoader(Context context, int courseDetailsDataType, String key) {
        return mAppDatabaseHelper.getCourseDetailsCursorLoader(context, courseDetailsDataType, key);
    }

    @Override
    public void prepareForDBUpdate(Context context, List<Course> courses) {
        mAppDatabaseHelper.prepareForDBUpdate(context, courses);
    }
}
