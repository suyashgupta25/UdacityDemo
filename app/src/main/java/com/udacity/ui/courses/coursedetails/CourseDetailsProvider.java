package com.udacity.ui.courses.coursedetails;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by suyashg on 11/07/18.
 */
@Module
public abstract class CourseDetailsProvider {

    @ContributesAndroidInjector(modules = CourseDetailsModule.class)
    abstract CourseDetailsFragment provideCourseDetailsFragmentFactory();
}
