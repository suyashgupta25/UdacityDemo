package com.udacity.data.database.courses;

import android.content.Context;
import android.support.v4.content.CursorLoader;

import com.udacity.data.model.api.courses.Course;

import java.util.List;

/**
 * Created by suyashg on 11/07/18.
 */

public interface DatabaseHelper {

    CursorLoader getCoursesListCursorLoader(Context context);

    CursorLoader getCourseDetailsCursorLoader(Context context, int courseDetailsDataType, String key);

    void prepareForDBUpdate(Context context, List<Course> courses);
}
