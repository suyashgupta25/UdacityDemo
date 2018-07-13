package com.udacity.data.model.api.courses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by suyashg on 05/07/18.
 */

public class CoursesListResponse {

    @Expose
    @SerializedName("courses")
    private List<Course> courses;

    @Expose
    @SerializedName("tracks")
    private List<Track> tracks;

    @Expose
    @SerializedName("degrees")
    private List<Degree> degrees;


    public List<Course> getCourses() {
        return courses;
    }
}
