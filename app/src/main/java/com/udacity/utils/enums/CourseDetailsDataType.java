package com.udacity.utils.enums;

/**
 * Created by suyashg on 11/07/18.
 */

public enum CourseDetailsDataType {
    COURSE(0),
    INSTRUCTOR(1);

    public int value;

    CourseDetailsDataType(int value)
    {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}


