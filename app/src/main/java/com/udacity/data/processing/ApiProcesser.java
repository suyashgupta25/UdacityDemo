package com.udacity.data.processing;

import com.udacity.data.model.api.courses.CourseTemplate;
import com.udacity.data.model.api.courses.CoursesListResponse;

import java.util.List;

/**
 * Created by suyashg on 06/07/18.
 */

public interface ApiProcesser {

    List<CourseTemplate> processCoursesResponse(CoursesListResponse response);

}
