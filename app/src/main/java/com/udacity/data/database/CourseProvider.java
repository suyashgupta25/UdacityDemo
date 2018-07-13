package com.udacity.data.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.utils.AppLogger;

/**
 * Created by suyashg on 08/07/18.
 */

public class CourseProvider extends ContentProvider {

    final String LOG_TAG = CourseProvider.class.getSimpleName();

    private CourseDbHelper mDbHelper;

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int COURSE = 100;
    static final int INSTRUCTOR = 102;

    static UriMatcher buildUriMatcher() {
        // 1) The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case. Add the constructor below.
        // NO_MATCH because we don't want the root node to match anything.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CourseContract.CONTENT_AUTHORITY;

        // 2) Use the addURI function to match each of the types.  Use the constants from
        // CourseContract to help define the types to the UriMatcher.
        matcher.addURI(authority, CourseContract.PATH_COURSE, COURSE);
        matcher.addURI(authority, CourseContract.PATH_INSTRUCTOR, INSTRUCTOR);

        // 3) Return the new matcher!
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new CourseDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        AppLogger.d(LOG_TAG, "query()");
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case COURSE: {
                retCursor = mDbHelper.getReadableDatabase().query(CourseContract.CourseEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case INSTRUCTOR: {
                retCursor = mDbHelper.getReadableDatabase().query(CourseContract.InstructorEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        AppLogger.d(LOG_TAG, "getType()");
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case COURSE:
                return CourseContract.CourseEntry.CONTENT_TYPE;
            case INSTRUCTOR:
                return CourseContract.InstructorEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case COURSE: {
                long _id = db.insert(CourseContract.CourseEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = CourseContract.CourseEntry.buildCourseUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case INSTRUCTOR: {
                long _id = db.insert(CourseContract.InstructorEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = CourseContract.InstructorEntry.buildInstructorUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        //Use this to notify any registered observers
        getContext().getContentResolver().notifyChange(uri, null);
        //Do not have db.close statements in your content provider.
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Student: Use the uriMatcher to match the COURSE and INSTRUCTOR URI's we are going to
        // handle.  If it doesn't match these, throw an UnsupportedOperationException.
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case COURSE: {
                rowsDeleted = db.delete(CourseContract.CourseEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case INSTRUCTOR: {
                rowsDeleted = db.delete(CourseContract.InstructorEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Student: A null value deletes all rows.  In my implementation of this, I only notified
        // the uri listeners (using the content resolver) if the rowsDeleted != 0 or the selection
        // is null.
        // Oh, and you should notify the listeners here.

        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        // This is a lot like the delete function.  We return the number of rows impacted
        // by the update.
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case COURSE: {
                rowsUpdated = db.update(CourseContract.CourseEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case INSTRUCTOR: {
                rowsUpdated = db.update(CourseContract.InstructorEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        return super.bulkInsert(uri, values);
    }

    @Override
    public void shutdown() {
        mDbHelper.close();
        super.shutdown();
    }
}
