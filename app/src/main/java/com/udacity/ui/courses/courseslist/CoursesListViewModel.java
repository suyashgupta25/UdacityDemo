package com.udacity.ui.courses.courseslist;

import android.content.Context;
import android.support.test.espresso.IdlingResource;
import android.support.v4.content.CursorLoader;

import com.udacity.data.DataManager;
import com.udacity.data.model.api.ApiError;
import com.udacity.data.model.api.courses.CourseTemplate;
import com.udacity.ui.base.BaseViewModel;
import com.udacity.utils.AppLogger;
import com.udacity.utils.ParsingUtils;
import com.udacity.utils.rx.SchedulerProvider;
import com.udacity.utils.testing.SimpleIdlingResource;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.utils.AppConstants.ERROR_DEFAULT_CODE;
import static com.udacity.utils.AppConstants.ERROR_DEFAULT_MSG;
import static com.udacity.utils.AppConstants.ERROR_DEFAULT_STATUS;

/**
 * Created by suyashg on 06/07/18.
 */

public class CoursesListViewModel extends BaseViewModel<CoursesListNavigator> {

    public CoursesListViewModel(SchedulerProvider mSchedulerProvider, DataManager mDataManager) {
        super(mSchedulerProvider, mDataManager);
        mIdlingResource = new SimpleIdlingResource();
    }

    // This call is not used for not since we are using sync adapter
    // but in case we switch to normal web call then we switch to this method
    public void fetchCoursesList(Context context) {
        setIsLoading(true);
        mIdlingResource.setIdleState(false);
        getCompositeDisposable().add(getDataManager()
                .getCourses()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    AppLogger.d(response.toString());
                    if (response != null) {
                        getDataManager().prepareForDBUpdate(context, response.getCourses());
                        getNavigator().refreshUI(new ArrayList<>());
                    } else {
                        ApiError apiError = new ApiError(ERROR_DEFAULT_CODE, ERROR_DEFAULT_STATUS, ERROR_DEFAULT_MSG);
                        getNavigator().handleError(apiError);
                    }
                    mIdlingResource.setIdleState(true);
                }, throwable -> {
                    ApiError apiError = ParsingUtils.parseError(throwable);
                    AppLogger.e(throwable.getMessage(), throwable);
                    getNavigator().handleError(apiError);
                    setIsLoading(false);
                    mIdlingResource.setIdleState(true);
                }));
    }

    public CursorLoader getCursorLoader(Context context) {
        return getDataManager().getCoursesListCursorLoader(context);
    }

}
