package com.udacity.ui.courses.courseslist;

import android.database.Cursor;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.udacity.utils.AppConstants;

/**
 * Created by suyashg on 06/07/18.
 */

public class CoursesListItemViewModel {

    private final String mKey;

    private final CourseItemViewModelListener viewModelListener;

    public final ObservableField<String> courseTitle;

    public final ObservableField<String> courseSummary;

    public final ObservableField<String> duration;

    public final ObservableField<String> level;

    public final ObservableField<String> imageUrl;

    public CoursesListItemViewModel(Cursor mCursor, CourseItemViewModelListener viewModelListener) {
        this.mKey = mCursor.getString(1);
        this.viewModelListener = viewModelListener;
        courseTitle = new ObservableField<>(mCursor.getString(2));
        courseSummary = new ObservableField<>(mCursor.getString(3));
        duration = new ObservableField<>(mCursor.getString(5)+ AppConstants.SPACE +mCursor.getString(6));
        String levelValue = mCursor.getString(4);
        if (!TextUtils.isEmpty(levelValue)) {
            levelValue = levelValue.substring(0,1).toUpperCase() + levelValue.substring(1);
        }
        level = new ObservableField<>(levelValue);
        if (!TextUtils.isEmpty(mCursor.getString(7))) {
            imageUrl = new ObservableField<>(mCursor.getString(7));
        } else if (!TextUtils.isEmpty(mCursor.getString(8))) {
            imageUrl = new ObservableField<>(mCursor.getString(8));
        } else {
            imageUrl = new ObservableField<>("");
        }
    }

    public void onItemClick() {
        viewModelListener.onItemClick(mKey);
    }

    public interface CourseItemViewModelListener {

        void onItemClick(String key);
    }

}
