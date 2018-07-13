package com.udacity.ui.courses.courseslist;

import android.content.Context;

import com.udacity.data.DataManager;
import com.udacity.data.model.api.courses.CoursesListResponse;
import com.udacity.utils.rx.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Created by suyashg on 13/07/18.
 */

@RunWith(MockitoJUnitRunner.class)
public class CoursesListItemViewModelTest {

    @Mock
    CoursesListNavigator mCoursesListNavigator;

    @Mock
    DataManager mMockDataManager;

    @Mock
    Context context;

    private CoursesListViewModel mCoursesListViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mCoursesListViewModel = new CoursesListViewModel(testSchedulerProvider, mMockDataManager);
        mCoursesListViewModel.setNavigator(mCoursesListNavigator);
    }

    @Test
    public void testCourseListSuccess() {
        CoursesListResponse coursesListResponse = new CoursesListResponse();
        doReturn(Single.just(coursesListResponse))
                .when(mMockDataManager)
                .getCourses();

        mCoursesListViewModel.fetchCoursesList(context);
        mTestScheduler.triggerActions();

        verify(mCoursesListNavigator).refreshUI(new ArrayList<>());
    }

    @After
    public void tearDown() throws Exception {
        mTestScheduler = null;
        mCoursesListViewModel = null;
        mCoursesListNavigator = null;
    }

}
