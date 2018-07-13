package com.udacity.ui.courses.courseslist;

import com.udacity.data.model.api.ApiError;
import com.udacity.data.model.api.courses.CourseTemplate;

import java.util.List;

/**
 * Created by suyashg on 06/07/18.
 */

public interface CoursesListNavigator {
    void handleError(ApiError apiError);

    void refreshUI(List<CourseTemplate> courseTemplates);
}
