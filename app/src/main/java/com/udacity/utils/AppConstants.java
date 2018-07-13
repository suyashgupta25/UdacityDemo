package com.udacity.utils;

/**
 * Created by suyashg on 06/07/18.
 */

public final class AppConstants {

    //HTTP CLIENT CONSTANTS
    public static final String PARAM_VALUE_CONTENT_TYPE = "application/json";

    //DEFAULT ERROR WITH MESSAGE
    public static final int ERROR_DEFAULT_CODE = -999;

    public static final String ERROR_DEFAULT_STATUS = "Bad Request";

    public static final String ERROR_DEFAULT_MSG = "We are facing some issues. Please try again later";


    //DATABASE
    public static final String DATABASE_NAME = "courses.db";
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;

    public static final int MAX_AUTHORS_SUPPORT = 3;

    public static final int VIEW_ = 3;


    //TEXT UTILS
    public static final String EMPTY = "";
    public static final String SPACE = " ";

}
