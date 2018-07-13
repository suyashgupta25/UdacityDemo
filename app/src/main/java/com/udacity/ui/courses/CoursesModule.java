package com.udacity.ui.courses;

import com.udacity.data.DataManager;
import com.udacity.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by suyashg on 06/07/18.
 */
@Module
public class CoursesModule {

    @Provides
    CoursesViewModel provideCoursesViewModel(SchedulerProvider schedulerProvider, DataManager dataManager) {
        return new CoursesViewModel(schedulerProvider, dataManager);
    }
}
