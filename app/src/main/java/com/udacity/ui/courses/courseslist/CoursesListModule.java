package com.udacity.ui.courses.courseslist;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.udacity.data.DataManager;
import com.udacity.utils.ViewModelProviderFactory;
import com.udacity.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by suyashg on 06/07/18.
 */
@Module
public class CoursesListModule {

    @Provides
    CoursesListViewModel coursesListViewModel(SchedulerProvider schedulerProvider, DataManager dataManager) {
        return new CoursesListViewModel(schedulerProvider, dataManager);
    }

    @Provides
    CoursesListAdapter provideCoursesListAdapter(CoursesListFragment fragment) {
        CoursesListAdapter coursesListAdapter = new CoursesListAdapter(fragment);
        return coursesListAdapter;
    }

    @Provides
    ViewModelProvider.Factory provideCoursesListViewModel(CoursesListViewModel coursesListViewModel) {
        return new ViewModelProviderFactory<>(coursesListViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(CoursesListFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
