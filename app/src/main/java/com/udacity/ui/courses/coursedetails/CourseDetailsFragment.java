package com.udacity.ui.courses.coursedetails;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.udacity.BR;
import com.udacity.R;
import com.udacity.databinding.FragmentCourseDetailsBinding;
import com.udacity.ui.base.BaseFragment;
import com.udacity.utils.AppLogger;
import com.udacity.utils.enums.CourseDetailsDataType;

import javax.inject.Inject;

/**
 * Created by suyashg on 11/07/18.
 */

public class CourseDetailsFragment extends BaseFragment<FragmentCourseDetailsBinding, CourseDetailsViewModel> implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private CourseDetailsViewModel mCourseDetailsViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_course_details;
    }

    @Override
    public CourseDetailsViewModel getViewModel() {
        mCourseDetailsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CourseDetailsViewModel.class);
        return mCourseDetailsViewModel;
    }

    public static CourseDetailsFragment newInstance(Context context, String courseKey) {
        CourseDetailsFragment fragment = new CourseDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.param_course_details_key), courseKey);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    private void setUp() {
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            supportActionBar.setTitle(R.string.label_toolbar_course_details);
            View toolbarView = getActivity().findViewById(R.id.toolbar);
            if (toolbarView != null) {
                ((Toolbar) toolbarView).setNavigationOnClickListener(this);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(CourseDetailsDataType.COURSE.getValue(), null, this);
        getLoaderManager().initLoader(CourseDetailsDataType.INSTRUCTOR.getValue(), null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mCourseDetailsViewModel.getCourseDetailsCursorLoader(getActivity(), id,
                getArguments().getString(getString(R.string.param_course_details_key)));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        AppLogger.d("onLoadFinished INSTRUCTOR");
        if (CourseDetailsDataType.COURSE.getValue() == loader.getId()) {
            mCourseDetailsViewModel.updateBindedCourseData(getActivity(), data);
        } else if (CourseDetailsDataType.INSTRUCTOR.getValue() == loader.getId()) {
            mCourseDetailsViewModel.updateBindedInstructorData(getActivity(), data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        AppLogger.d("onLoaderReset");
        loader.cancelLoad();
    }

    @Override
    public void onClick(View view) {
        if (view.getParent() instanceof Toolbar) {
            getActivity().onBackPressed();
        }
    }
}
