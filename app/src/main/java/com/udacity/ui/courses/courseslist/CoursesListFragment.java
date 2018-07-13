package com.udacity.ui.courses.courseslist;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewTreeObserver;

import com.udacity.BR;
import com.udacity.R;
import com.udacity.data.model.api.ApiError;
import com.udacity.data.model.api.courses.CourseTemplate;
import com.udacity.databinding.FragmentCoursesListBinding;
import com.udacity.ui.base.BaseFragment;
import com.udacity.ui.courses.coursedetails.CourseDetailsFragment;
import com.udacity.utils.AppLogger;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by suyashg on 06/07/18.
 */

public class CoursesListFragment extends BaseFragment<FragmentCoursesListBinding, CoursesListViewModel>
    implements CoursesListNavigator, CoursesListAdapter.CoursesListAdapterListener, LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = CoursesListFragment.class.getSimpleName();

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Inject
    CoursesListAdapter mCoursesListAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    private final static int COURSES_LOADER = 0;
    private CoursesListViewModel mCoursesListViewModel;
    private FragmentCoursesListBinding mFragmentCoursesListBinding;

    public static CoursesListFragment newInstance() {
        CoursesListFragment fragment = new CoursesListFragment();
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_courses_list;
    }

    @Override
    public CoursesListViewModel getViewModel() {
        mCoursesListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CoursesListViewModel.class);
        return mCoursesListViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoursesListViewModel.setNavigator(this);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mErrorReceiver,
                new IntentFilter(this.getString(R.string.action_courses_error)));
    }

    private BroadcastReceiver mErrorReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mCoursesListViewModel.setIsLoading(false);
            ApiError error = intent.getParcelableExtra(getString(R.string.param_courses_error));
            AppLogger.e(LOG_TAG, error.getMessage());
            handleError(error);
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentCoursesListBinding = getViewDataBinding();
        setUp();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //called manually instead of sync adapter since in case of first install sync adapter wont work if no internet
        mCoursesListViewModel.fetchCoursesList(getActivity());
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentCoursesListBinding.rlCoursesList.setLayoutManager(mLayoutManager);
        mFragmentCoursesListBinding.rlCoursesList.setItemAnimator(new DefaultItemAnimator());
        mFragmentCoursesListBinding.rlCoursesList.setAdapter(mCoursesListAdapter);
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setTitle(R.string.label_toolbar_courses_list);
        }
    }

    @Override
    public void onRetryClick() {
        mCoursesListViewModel.fetchCoursesList(getActivity());
    }

    @Override
    public void onCourseClicked(String key) {
        CourseDetailsFragment courseDetailsFragment = CourseDetailsFragment.newInstance(getActivity(), key);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.add(R.id.fl_courses_container, courseDetailsFragment, CourseDetailsFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void handleError(ApiError apiError) {
        mCoursesListViewModel.setIsLoading(false);
        if(this.isNetworkConnected()) {
            mCoursesListAdapter.clearItemsForError(apiError.getMessage());
        } else {
            mCoursesListAdapter.clearItemsForError(getString(R.string.error_no_internet));
        }
    }

    @Override
    public void refreshUI(List<CourseTemplate> courseTemplates) {
        getLoaderManager().initLoader(COURSES_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = mCoursesListViewModel.getCursorLoader(getActivity());
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCoursesListAdapter.swapCursor(data);
        if (data.getCount() == 0) {
            getActivity().supportStartPostponedEnterTransition();
            mCoursesListViewModel.setIsLoading(false);
        } else {
            mFragmentCoursesListBinding.rlCoursesList.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    // Since we know we're going to get items, we keep the listener around until
                    // we see Children.
                    if (mFragmentCoursesListBinding.rlCoursesList.getChildCount() > 0) {
                        mFragmentCoursesListBinding.rlCoursesList.getViewTreeObserver().removeOnPreDrawListener(this);
                        getActivity().supportStartPostponedEnterTransition();
                        mCoursesListViewModel.setIsLoading(false);
                        return true;
                    }
                    return false;
                }
            });
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCoursesListAdapter.swapCursor(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mErrorReceiver);
    }

    public IdlingResource getIdlingResource() {
        return mCoursesListViewModel.getIdlingResource();
    }
}
