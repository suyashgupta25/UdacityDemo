package com.udacity.data.model.api.courses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by suyashg on 07/07/18.
 */

public class Track {

    @Expose
    @SerializedName("courses")
    private List<String> courses;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("description")
    private String description;
}
