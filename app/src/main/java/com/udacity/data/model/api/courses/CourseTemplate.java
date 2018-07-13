package com.udacity.data.model.api.courses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by suyashg on 06/07/18.
 */

public class CourseTemplate {

    @Expose
    @SerializedName("key")
    private String key;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("short_summary")
    private String shortSummary;

    @Expose
    @SerializedName("expected_duration")
    private int expectedDuration;

    @Expose
    @SerializedName("expected_duration_unit")
    private String expectedDurationUnit;

    @Expose
    @SerializedName("new_release")
    private boolean isNewRelease;

    @Expose
    @SerializedName("featured")
    private boolean isFeatured;

    @Expose
    @SerializedName("starter")
    private boolean isStarter;

}
