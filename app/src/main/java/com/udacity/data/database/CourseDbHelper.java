package com.udacity.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.data.database.CourseContract.CourseEntry;
import com.udacity.data.database.CourseContract.InstructorEntry;
import com.udacity.utils.AppConstants;
import com.udacity.utils.AppLogger;


/**
 * Created by suyashg on 08/07/18.
 */

public class CourseDbHelper extends SQLiteOpenHelper {

    final String LOG_TAG = CourseDbHelper.class.getSimpleName();

    public CourseDbHelper(Context context) {
        super(context, AppConstants.DATABASE_NAME, null, AppConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_COURSE_TABLE = "CREATE TABLE " + CourseEntry.TABLE_NAME + " (" +
                // Unique keys will be auto-generated since to unique id coming from server.
                CourseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                CourseEntry.COLUMN_KEY + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_SUBTITLE + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_IMAGE_URL + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_EXPT_LEARN + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_FEATURED + " INTEGER DEFAULT 0, " +
                CourseEntry.COLUMN_PROJECT_NAME + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_REQ_KNOWLEDGE + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_SYLLABUS + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_NEW_RELEASE + " INTEGER DEFAULT 0, " +
                CourseEntry.COLUMN_HOMEPAGE + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_PROJ_DESC + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_FULL_COURSE + " INTEGER DEFAULT 0, " +
                CourseEntry.COLUMN_FAQ + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_BANNER_IMG + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_SHORT_SUMM + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_SLUG + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_STARTER + " INTEGER DEFAULT 0, " +
                CourseEntry.COLUMN_LEVEL + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_EXP_DURATION_UNIT + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_EXP_DURATION + " INTEGER DEFAULT 0, " +
                CourseEntry.COLUMN_SUMMARY + " TEXT NOT NULL, " +

                // To assure the application have just one course with unique key
                // we have created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + CourseEntry.COLUMN_KEY + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_INSTRUCTOR_TABLE = "CREATE TABLE " + InstructorEntry.TABLE_NAME + " (" +
                InstructorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                InstructorEntry.COLUMN_BIO + " TEXT NOT NULL, " +
                InstructorEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                InstructorEntry.COLUMN_KEY + " TEXT NOT NULL, " +
                InstructorEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                InstructorEntry.COLUMN_IDENTIFIER + " TEXT NOT NULL, " +

                // To assure the application have instructor with unique key
                // we have created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + InstructorEntry.COLUMN_IDENTIFIER + ") ON CONFLICT REPLACE);";

        AppLogger.d(LOG_TAG, SQL_CREATE_COURSE_TABLE);
        AppLogger.d(LOG_TAG, SQL_CREATE_INSTRUCTOR_TABLE);
        db.execSQL(SQL_CREATE_COURSE_TABLE);
        db.execSQL(SQL_CREATE_INSTRUCTOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        db.execSQL("DROP TABLE IF EXISTS " + CourseEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + InstructorEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=1;");
    }
}
