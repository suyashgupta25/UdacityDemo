package com.udacity.data;

import com.udacity.data.database.courses.DatabaseHelper;
import com.udacity.data.processing.ApiProcesser;
import com.udacity.data.remote.ApiHelper;

/**
 * Created by suyashg on 05/07/18.
 */

public interface DataManager extends ApiHelper, ApiProcesser, DatabaseHelper {
}
