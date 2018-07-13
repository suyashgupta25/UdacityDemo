package com.udacity.data.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by suyashg on 06/07/18.
 */

public class ApiError implements Parcelable {

    private int errorCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    public ApiError(int errorCode, String statusCode, String message) {
        this.errorCode = errorCode;
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.errorCode);
        dest.writeString(this.message);
        dest.writeString(this.statusCode);
    }

    protected ApiError(Parcel in) {
        this.errorCode = in.readInt();
        this.message = in.readString();
        this.statusCode = in.readString();
    }

    public static final Parcelable.Creator<ApiError> CREATOR = new Parcelable.Creator<ApiError>() {
        @Override
        public ApiError createFromParcel(Parcel source) {
            return new ApiError(source);
        }

        @Override
        public ApiError[] newArray(int size) {
            return new ApiError[size];
        }
    };
}
