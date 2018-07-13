package com.udacity.data.model.api.courses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by suyashg on 07/07/18.
 */

public class CourseTeaserVideo {

    @Expose
    @SerializedName("youtube_url")
    private String youtubeURL;

}
