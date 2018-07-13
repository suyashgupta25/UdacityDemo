package com.udacity.ui.courses;

import com.udacity.data.DataManager;
import com.udacity.ui.base.BaseViewModel;
import com.udacity.utils.rx.SchedulerProvider;

/**
 * Created by suyashg on 06/07/18.
 */

public class CoursesViewModel extends BaseViewModel<CoursesNavigator> {


    public CoursesViewModel(SchedulerProvider mSchedulerProvider, DataManager mDataManager) {
        super(mSchedulerProvider, mDataManager);
    }
}
