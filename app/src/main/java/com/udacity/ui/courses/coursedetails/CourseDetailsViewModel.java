package com.udacity.ui.courses.coursedetails;

import android.content.Context;
import android.database.Cursor;
import android.databinding.ObservableField;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.udacity.R;
import com.udacity.data.DataManager;
import com.udacity.data.model.databinding.CourseAuthor;
import com.udacity.ui.base.BaseViewModel;
import com.udacity.utils.AppConstants;
import com.udacity.utils.AppLogger;
import com.udacity.utils.rx.SchedulerProvider;

import static com.udacity.utils.AppConstants.MAX_AUTHORS_SUPPORT;

/**
 * Created by suyashg on 11/07/18.
 */

public class CourseDetailsViewModel extends BaseViewModel<CourseDetailsNavigator> {

    public ObservableField<String> courseTitle = new ObservableField<>("");

    public ObservableField<String> courseDesc = new ObservableField<>("");

    public ObservableField<String> duration = new ObservableField<>("");

    public ObservableField<String> level = new ObservableField<>("");

    public ObservableField<String> imageUrl = new ObservableField<>("");

    public ObservableField<CourseAuthor> author1 = new ObservableField<>(new CourseAuthor());

    public ObservableField<CourseAuthor> author2 = new ObservableField<>(new CourseAuthor());

    public ObservableField<CourseAuthor> author3 = new ObservableField<>(new CourseAuthor());

    public CourseDetailsViewModel(SchedulerProvider mSchedulerProvider, DataManager mDataManager) {
        super(mSchedulerProvider, mDataManager);
    }

    public void updateBindedCourseData(Context context, Cursor cursor) {
        try {
            cursor.moveToFirst();
            courseTitle.set(cursor.getString(2));
            courseDesc.set(cursor.getString(3));
            duration.set(cursor.getString(5) + AppConstants.SPACE + cursor.getString(6));
            String levelValue = cursor.getString(4);
            if (!TextUtils.isEmpty(levelValue)) {
                levelValue = levelValue.substring(0, 1).toUpperCase() + levelValue.substring(1);
            }
            level.set(levelValue);
            if (!TextUtils.isEmpty(cursor.getString(7))) {
                imageUrl.set(cursor.getString(7));
            } else if (!TextUtils.isEmpty(cursor.getString(8))) {
                imageUrl.set(cursor.getString(8));
            }
        } finally {
            cursor.close();
        }
    }

    public void updateBindedInstructorData(Context context, Cursor cursor) {
//        cursor.moveToFirst();
        AppLogger.d("updateBindedInstructorData ="+cursor.getCount());
        try {
            int authorsCount = MAX_AUTHORS_SUPPORT;
            while (cursor.moveToNext() && authorsCount != 0) {
                CourseAuthor authorTemp = new CourseAuthor();
                authorTemp.name = cursor.getString(2);
                authorTemp.bio = cursor.getString(3);
                authorTemp.image = cursor.getString(4);
                if (authorsCount == 3) {
                    author1.set(authorTemp);
                } else if (authorsCount == 2) {
                    author2.set(authorTemp);
                } else if (authorsCount == 1) {
                    author3.set(authorTemp);
                }
                --authorsCount;
            }
        } finally {
            cursor.close();
        }

    }


    public CursorLoader getCourseDetailsCursorLoader(Context context, int courseDetailsDataType, String key) {
        return getDataManager().getCourseDetailsCursorLoader(context, courseDetailsDataType, key);
    }
}
