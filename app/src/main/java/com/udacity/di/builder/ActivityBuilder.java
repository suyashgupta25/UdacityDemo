package com.udacity.di.builder;

import com.udacity.ui.courses.CoursesActivity;
import com.udacity.ui.courses.CoursesModule;
import com.udacity.ui.courses.coursedetails.CourseDetailsProvider;
import com.udacity.ui.courses.courseslist.CoursesListProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by suyashg on 05/07/18.
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            CoursesModule.class,
            CoursesListProvider.class,
            CourseDetailsProvider.class})
    abstract CoursesActivity bindCoursesActivity();

}
