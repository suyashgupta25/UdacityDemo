package com.udacity.data.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by suyashg on 08/07/18.
 */

public class CourseContract {


    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.udacity";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.udacity.app/course/ is a valid path for
    // looking at course data. content://com.example.android.udacity.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    public static final String PATH_COURSE = "course";

//    public static final String PATH_COURSE_INS = "course_instructor";

    public static final String PATH_INSTRUCTOR = "instructor";

    /* Inner class that defines the contents of the course table */
    public static final class CourseEntry implements BaseColumns {

        public static final String TABLE_NAME = "course";

        // Column with course key
        public static final String COLUMN_KEY = "key";

        public static final String COLUMN_SUBTITLE = "subtitle";

        public static final String COLUMN_IMAGE_URL = "image";

        public static final String COLUMN_EXPT_LEARN = "expected_learning";

        public static final String COLUMN_FEATURED = "featured";

        public static final String COLUMN_PROJECT_NAME = "project_name";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_REQ_KNOWLEDGE = "required_knowledge";

        public static final String COLUMN_SYLLABUS = "syllabus";

        public static final String COLUMN_NEW_RELEASE = "new_release";

        public static final String COLUMN_HOMEPAGE = "homepage";

        public static final String COLUMN_PROJ_DESC = "project_description";

        public static final String COLUMN_FULL_COURSE = "full_course_available";

        public static final String COLUMN_FAQ = "faq";

        public static final String COLUMN_BANNER_IMG = "banner_image";

        public static final String COLUMN_SHORT_SUMM = "short_summary";

        public static final String COLUMN_SLUG = "slug";

        public static final String COLUMN_STARTER = "starter";

        public static final String COLUMN_LEVEL = "level";

        public static final String COLUMN_EXP_DURATION_UNIT = "expected_duration_unit";

        public static final String COLUMN_EXP_DURATION = "expected_duration";

        public static final String COLUMN_SUMMARY = "summary";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE;

        public static Uri buildCourseUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class InstructorEntry implements BaseColumns {
        public static final String TABLE_NAME = "instructor";

        public static final String COLUMN_KEY = "key_course";
        public static final String COLUMN_BIO = "bio";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IDENTIFIER = "identifier";

        public static final Uri INSTRUCTOR_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INSTRUCTOR).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INSTRUCTOR;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INSTRUCTOR;

        public static Uri buildInstructorUri(long id) {
            return ContentUris.withAppendedId(INSTRUCTOR_URI, id);
        }

    }

}
