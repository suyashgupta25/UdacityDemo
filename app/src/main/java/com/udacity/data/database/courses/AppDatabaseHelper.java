package com.udacity.data.database.courses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.CursorLoader;

import com.udacity.data.database.CourseContract;
import com.udacity.data.model.api.courses.Course;
import com.udacity.data.model.api.courses.Instructor;
import com.udacity.utils.enums.CourseDetailsDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.inject.Inject;

/**
 * Created by suyashg on 11/07/18.
 */

public class AppDatabaseHelper implements DatabaseHelper {

    private final String SORT_OREDER = CourseContract.CourseEntry.COLUMN_FEATURED + " DESC";

    public static final String[] COURSES_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the course & instructor tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the course table
            // using the instructor key , which is only in the instructor table.
            // So the convenience is worth it.
            CourseContract.CourseEntry.TABLE_NAME + "." + CourseContract.CourseEntry._ID,//0
            CourseContract.CourseEntry.COLUMN_KEY,//1
            CourseContract.CourseEntry.COLUMN_TITLE,//2
            CourseContract.CourseEntry.COLUMN_SHORT_SUMM,//3
            CourseContract.CourseEntry.COLUMN_LEVEL,//4
            CourseContract.CourseEntry.COLUMN_EXP_DURATION,//5
            CourseContract.CourseEntry.COLUMN_EXP_DURATION_UNIT,//6
            CourseContract.CourseEntry.COLUMN_IMAGE_URL,//7
            CourseContract.CourseEntry.COLUMN_BANNER_IMG,//8
            CourseContract.CourseEntry.COLUMN_FEATURED //9

    };

    public static final String[] INSTRUCTOR_COLUMNS = {
            CourseContract.InstructorEntry.TABLE_NAME + "." + CourseContract.InstructorEntry._ID,//0
            CourseContract.InstructorEntry.COLUMN_KEY,//1
            CourseContract.InstructorEntry.COLUMN_NAME,//2
            CourseContract.InstructorEntry.COLUMN_BIO,//3
            CourseContract.InstructorEntry.COLUMN_IMAGE,//4
    };

    @Inject
    public AppDatabaseHelper() {
    }

    @Override
    public CursorLoader getCoursesListCursorLoader(Context context) {
        Uri contentUri = CourseContract.CourseEntry.CONTENT_URI;
        String[] coursesColumns = COURSES_COLUMNS;
        CursorLoader cursorLoader = new CursorLoader(context, contentUri, coursesColumns,
                null, null, SORT_OREDER);
        return cursorLoader;
    }

    @Override
    public CursorLoader getCourseDetailsCursorLoader(Context context, int courseDetailsDataType, String key) {
        CursorLoader cursorLoader = null;
        String[] selectionArgs = {key};
        if (CourseDetailsDataType.COURSE.getValue() == courseDetailsDataType) {
            Uri contentUri = CourseContract.CourseEntry.CONTENT_URI;
            String selection = CourseContract.CourseEntry.COLUMN_KEY + "=?";
            cursorLoader = new CursorLoader(context, contentUri, COURSES_COLUMNS,
                    selection, selectionArgs, null);
        } else if (CourseDetailsDataType.INSTRUCTOR.getValue() == courseDetailsDataType) {
            Uri instructorUri = CourseContract.InstructorEntry.INSTRUCTOR_URI;
            String selection = CourseContract.InstructorEntry.COLUMN_KEY + "=?";
            cursorLoader = new CursorLoader(context, instructorUri, INSTRUCTOR_COLUMNS,
                    selection, selectionArgs, null);
        }
        return cursorLoader;
    }

    @Override
    public void prepareForDBUpdate(Context context, List<Course> courses) {
        Vector<ContentValues> cVVector = new Vector<ContentValues>(courses.size());
        Vector<ContentValues> iVVector = new Vector<ContentValues>();
        ArrayList<String> coursesIds = new ArrayList<>();

        //get course id's from db to perform a check whether any course is removed from server
        Cursor cursor = context.getContentResolver().query(CourseContract.CourseEntry.CONTENT_URI,
                new String[]{CourseContract.CourseEntry.COLUMN_KEY}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    coursesIds.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        for (Course course : courses) {
            ContentValues courseValues = prepareCourseContent(course);
            if (!course.getInstructors().isEmpty()) {
                for (Instructor instructor : course.getInstructors()) {
                    iVVector.add(prepareInstructorContent(instructor, course));
                }
            }
            if (coursesIds.contains(course.getKey())) {
                coursesIds.remove(course.getKey());
            }
            cVVector.add(courseValues);
        }

        // add to database
        if (cVVector.size() > 0) {
            // call bulkInsert to add the course Entries to the database here
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            context.getContentResolver().bulkInsert(CourseContract.CourseEntry.CONTENT_URI, cvArray);
            for (String id : coursesIds) {
                context.getContentResolver().delete(CourseContract.CourseEntry.CONTENT_URI,
                        CourseContract.CourseEntry.COLUMN_KEY + " = " + id, null);
            }
        }

        if (iVVector.size() > 0) {
            ContentValues[] ivArray = new ContentValues[iVVector.size()];
            iVVector.toArray(ivArray);
            context.getContentResolver().bulkInsert(CourseContract.InstructorEntry.INSTRUCTOR_URI, ivArray);
        }

    }

    private ContentValues prepareCourseContent(Course course) {
        ContentValues courseValues = new ContentValues();
        courseValues.put(CourseContract.CourseEntry.COLUMN_KEY, course.getKey());
        courseValues.put(CourseContract.CourseEntry.COLUMN_SUBTITLE, course.getSubtitle());
        courseValues.put(CourseContract.CourseEntry.COLUMN_IMAGE_URL, course.getImageURL());
        courseValues.put(CourseContract.CourseEntry.COLUMN_EXPT_LEARN, course.getExpectedLearning());
        courseValues.put(CourseContract.CourseEntry.COLUMN_FEATURED, course.isFeatured());
        courseValues.put(CourseContract.CourseEntry.COLUMN_PROJECT_NAME, course.getProjectName());
        courseValues.put(CourseContract.CourseEntry.COLUMN_TITLE, course.getTitle());
        courseValues.put(CourseContract.CourseEntry.COLUMN_REQ_KNOWLEDGE, course.getRequiredKnowledge());
        courseValues.put(CourseContract.CourseEntry.COLUMN_SYLLABUS, course.getSyllabus());
        courseValues.put(CourseContract.CourseEntry.COLUMN_NEW_RELEASE, course.isNewRelease());
        courseValues.put(CourseContract.CourseEntry.COLUMN_HOMEPAGE, course.getHomepageURL());
        courseValues.put(CourseContract.CourseEntry.COLUMN_PROJ_DESC, course.getProjectDescription());
        courseValues.put(CourseContract.CourseEntry.COLUMN_FULL_COURSE, course.isFullCourseAvailable());
        courseValues.put(CourseContract.CourseEntry.COLUMN_FAQ, course.getFaq());
        courseValues.put(CourseContract.CourseEntry.COLUMN_BANNER_IMG, course.getBannerImageURL());
        courseValues.put(CourseContract.CourseEntry.COLUMN_SHORT_SUMM, course.getShortSummary());
        courseValues.put(CourseContract.CourseEntry.COLUMN_SLUG, course.getSlug());
        courseValues.put(CourseContract.CourseEntry.COLUMN_STARTER, course.isStarter());
        courseValues.put(CourseContract.CourseEntry.COLUMN_LEVEL, course.getLevel());
        courseValues.put(CourseContract.CourseEntry.COLUMN_EXP_DURATION_UNIT, course.getExpectedDurationUnit());
        courseValues.put(CourseContract.CourseEntry.COLUMN_EXP_DURATION, course.getExpectedDuration());
        courseValues.put(CourseContract.CourseEntry.COLUMN_SUMMARY, course.getSummary());
        return courseValues;
    }

    private ContentValues prepareInstructorContent(Instructor instructor, Course course) {
        ContentValues instructorValues = new ContentValues();
        instructorValues.put(CourseContract.InstructorEntry.COLUMN_BIO, instructor.getBiography());
        instructorValues.put(CourseContract.InstructorEntry.COLUMN_IMAGE, instructor.getImageURL());
        instructorValues.put(CourseContract.InstructorEntry.COLUMN_NAME, instructor.getName());
        instructorValues.put(CourseContract.InstructorEntry.COLUMN_KEY, course.getKey());
        instructorValues.put(CourseContract.InstructorEntry.COLUMN_IDENTIFIER, course.getKey() + instructor.getName());
        return instructorValues;
    }
}
