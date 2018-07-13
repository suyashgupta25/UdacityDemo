package com.udacity.utils;

import android.text.TextUtils;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.udacity.data.model.api.ApiError;

import static com.udacity.utils.AppConstants.ERROR_DEFAULT_CODE;
import static com.udacity.utils.AppConstants.ERROR_DEFAULT_MSG;
import static com.udacity.utils.AppConstants.ERROR_DEFAULT_STATUS;

/**
 * Created by suyashg on 06/07/18.
 */

public final class ParsingUtils {

    private ParsingUtils() {
        // This class is not publicly instantiable
    }

    public static ApiError parseError(Throwable throwable) {
        ApiError defaultError = new ApiError(ERROR_DEFAULT_CODE, ERROR_DEFAULT_STATUS, ERROR_DEFAULT_MSG);
        if (throwable instanceof ANError) {
            String errorBody = ((ANError) throwable).getErrorBody();
            if (!TextUtils.isEmpty(errorBody)) {
                //Server side custom error parsing and implementation
            } else {
                return new ApiError(((ANError) throwable).getErrorCode(),
                        ((ANError) throwable).getErrorDetail(),
                        throwable.getLocalizedMessage());
            }
        }
        return defaultError;
    }
}
