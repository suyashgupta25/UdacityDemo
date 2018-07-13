package com.udacity.data.remote;

import com.udacity.BuildConfig;

/**
 * Created by suyashg on 05/07/18.
 */

public final class ApiEndPoint {

    public static final String ENDPOINT_COURSES = BuildConfig.BASE_URL + "/v0/courses";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }
}

