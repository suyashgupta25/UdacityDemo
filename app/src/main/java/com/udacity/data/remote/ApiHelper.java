package com.udacity.data.remote;

import com.udacity.data.model.api.courses.CoursesListResponse;

import io.reactivex.Single;

/**
 * Created by suyashg on 05/07/18.
 */

public interface ApiHelper {

    Single<CoursesListResponse> getCourses();

}
