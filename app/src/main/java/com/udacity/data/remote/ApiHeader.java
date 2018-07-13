package com.udacity.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.di.ApiInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by suyashg on 05/07/18.
 */
@Singleton
public class ApiHeader {

    private ProtectedApiHeader mProtectedApiHeader;

    private PublicApiHeader mPublicApiHeader;

    @Inject
    public ApiHeader(PublicApiHeader publicApiHeader, ProtectedApiHeader protectedApiHeader) {
        mPublicApiHeader = publicApiHeader;
        mProtectedApiHeader = protectedApiHeader;
    }

    public PublicApiHeader getPublicApiHeader() {
        return mPublicApiHeader;
    }

    public static final class ProtectedApiHeader {
        //add any protected API header, if required
        @Inject
        public ProtectedApiHeader() {
        }
    }

    public static final class PublicApiHeader {

        @Expose
        @SerializedName("Content-Type")
        private String contentType;

        @Inject
        public PublicApiHeader(@ApiInfo String contentType) {
            this.contentType = contentType;
        }
    }

}
