package com.udacity.data.processing;

import com.udacity.data.model.api.courses.Course;
import com.udacity.data.model.api.courses.CourseTemplate;
import com.udacity.data.model.api.courses.CoursesListResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by suyashg on 06/07/18.
 */

public class AppApiProcessor implements ApiProcesser {

    @Inject
    public AppApiProcessor() {
    }


    @Override
    public List<CourseTemplate> processCoursesResponse(CoursesListResponse response) {
        List<Course> courses = response.getCourses();
        // extract the data required for the view making of the list here and add it template class.
        // This method is not used for now since we are using content resolver and sync adapter
        return new ArrayList<>();
    }
}
