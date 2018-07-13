package com.udacity.ui.courses.coursedetails;

import android.arch.lifecycle.ViewModelProvider;

import com.udacity.data.DataManager;
import com.udacity.utils.ViewModelProviderFactory;
import com.udacity.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by suyashg on 11/07/18.
 */
@Module
public class CourseDetailsModule {


    @Provides
    CourseDetailsViewModel courseDetailsViewModel(SchedulerProvider schedulerProvider, DataManager dataManager) {
        return new CourseDetailsViewModel(schedulerProvider, dataManager);
    }
    @Provides
    ViewModelProvider.Factory provideCourseDetailsViewModel(CourseDetailsViewModel courseDetailsViewModel) {
        return new ViewModelProviderFactory<>(courseDetailsViewModel);
    }
}
