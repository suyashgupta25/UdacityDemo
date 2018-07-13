package com.udacity.data.remote;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.udacity.data.model.api.courses.CoursesListResponse;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by suyashg on 05/07/18.
 */

public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public Single<CoursesListResponse> getCourses() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_COURSES)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .build()
                .getObjectSingle(CoursesListResponse.class);
    }

}
