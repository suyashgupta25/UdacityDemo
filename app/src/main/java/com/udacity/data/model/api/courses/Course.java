package com.udacity.data.model.api.courses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by suyashg on 07/07/18.
 */

public class Course {


    @Expose
    @SerializedName("key")
    private String key;

    @Expose
    @SerializedName("instructors")
    private List<Instructor> instructors;

    @Expose
    @SerializedName("subtitle")
    private String subtitle;

    @Expose
    @SerializedName("image")
    private String imageURL;

    @Expose
    @SerializedName("expected_learning")
    private String expectedLearning;

    @Expose
    @SerializedName("featured")
    private boolean isFeatured;

    @Expose
    @SerializedName("project_name")
    private String projectName;

    @Expose
    @SerializedName("teaser_video")
    private CourseTeaserVideo teaserVideo;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("related_degree_keys")
    private List<String> relatedDegreeKeys;

    @Expose
    @SerializedName("required_knowledge")
    private String requiredKnowledge;

    @Expose
    @SerializedName("syllabus")
    private String syllabus;

    @Expose
    @SerializedName("new_release")
    private boolean isNewRelease;

    @Expose
    @SerializedName("homepage")
    private String homepageURL;

    @Expose
    @SerializedName("project_description")
    private String projectDescription;

    @Expose
    @SerializedName("full_course_available")
    private boolean isFullCourseAvailable;

    @Expose
    @SerializedName("faq")
    private String faq;

    @Expose
    @SerializedName("affiliates")
    private List<CourseAffiliate> affiliates;

    @Expose
    @SerializedName("tracks")
    private List<String> tracks;

    @Expose
    @SerializedName("banner_image")
    private String bannerImageURL;

    @Expose
    @SerializedName("short_summary")
    private String shortSummary;

    @Expose
    @SerializedName("slug")
    private String slug;

    @Expose
    @SerializedName("starter")
    private boolean isStarter;

    @Expose
    @SerializedName("level")
    private String level;

    @Expose
    @SerializedName("expected_duration_unit")
    private String expectedDurationUnit;

    @Expose
    @SerializedName("summary")
    private String summary;

    @Expose
    @SerializedName("expected_duration")
    private int expectedDuration;

    public String getKey() {
        return key;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getExpectedLearning() {
        return expectedLearning;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public String getProjectName() {
        return projectName;
    }

    public CourseTeaserVideo getTeaserVideo() {
        return teaserVideo;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getRelatedDegreeKeys() {
        return relatedDegreeKeys;
    }

    public String getRequiredKnowledge() {
        return requiredKnowledge;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public boolean isNewRelease() {
        return isNewRelease;
    }

    public String getHomepageURL() {
        return homepageURL;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public boolean isFullCourseAvailable() {
        return isFullCourseAvailable;
    }

    public String getFaq() {
        return faq;
    }

    public List<CourseAffiliate> getAffiliates() {
        return affiliates;
    }

    public List<String> getTracks() {
        return tracks;
    }

    public String getBannerImageURL() {
        return bannerImageURL;
    }

    public String getShortSummary() {
        return shortSummary;
    }

    public String getSlug() {
        return slug;
    }

    public boolean isStarter() {
        return isStarter;
    }

    public String getLevel() {
        return level;
    }

    public String getExpectedDurationUnit() {
        return expectedDurationUnit;
    }

    public String getSummary() {
        return summary;
    }

    public int getExpectedDuration() {
        return expectedDuration;
    }
}
