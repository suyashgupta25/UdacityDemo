package com.udacity.ui.courses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.udacity.BR;
import com.udacity.R;
import com.udacity.databinding.ActivityCoursesBinding;
import com.udacity.ui.base.BaseActivity;
import com.udacity.ui.courses.courseslist.CoursesListFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by suyashg on 06/07/18.
 */

public class CoursesActivity extends BaseActivity<ActivityCoursesBinding, CoursesViewModel>
        implements HasSupportFragmentInjector, CoursesNavigator {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    CoursesViewModel coursesViewModel;
    private ActivityCoursesBinding activityCoursesBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_courses;
    }

    @Override
    public CoursesViewModel getViewModel() {
        return coursesViewModel;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCoursesBinding = getViewDataBinding();
        coursesViewModel.setNavigator(this);
        setup();
    }

    private void setup() {
        setSupportActionBar(activityCoursesBinding.toolbar);
        CoursesListFragment coursesListFragment = CoursesListFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(activityCoursesBinding.flCoursesContainer.getId(),
                        coursesListFragment, CoursesListFragment.class.getSimpleName())
                .commit();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
        });
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getCourseListIdlingResource() {
        CoursesListFragment fragmentByTag = (CoursesListFragment) getSupportFragmentManager().findFragmentByTag(CoursesListFragment.class.getSimpleName());
        return fragmentByTag.getIdlingResource();
    }
}
