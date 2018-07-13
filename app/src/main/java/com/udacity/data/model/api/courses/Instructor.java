package com.udacity.data.model.api.courses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by suyashg on 07/07/18.
 */

public class Instructor {

    @Expose
    @SerializedName("bio")
    private String biography;

    @Expose
    @SerializedName("image")
    private String imageURL;

    @Expose
    @SerializedName("name")
    private String name;


    public String getBiography() {
        return biography;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getName() {
        return name;
    }
}
