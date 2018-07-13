package com.udacity.ui.courses.courseslist;

import com.udacity.ui.courses.coursedetails.CourseDetailsFragment;
import com.udacity.ui.courses.coursedetails.CourseDetailsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by suyashg on 06/07/18.
 */
@Module
public abstract class CoursesListProvider {

    @ContributesAndroidInjector(modules = CoursesListModule.class)
    abstract CoursesListFragment provideCoursesListFragmentFactory();

}
