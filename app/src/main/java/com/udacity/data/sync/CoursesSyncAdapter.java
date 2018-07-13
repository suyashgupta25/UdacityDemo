package com.udacity.data.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.udacity.R;
import com.udacity.data.AppDataManager;
import com.udacity.data.database.CourseContract;
import com.udacity.data.database.courses.AppDatabaseHelper;
import com.udacity.data.model.api.ApiError;
import com.udacity.data.model.api.courses.Course;
import com.udacity.data.model.api.courses.CourseTemplate;
import com.udacity.data.model.api.courses.Instructor;
import com.udacity.data.remote.ApiHeader;
import com.udacity.data.remote.AppApiHelper;
import com.udacity.utils.AppConstants;
import com.udacity.utils.AppLogger;
import com.udacity.utils.ParsingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.udacity.utils.AppConstants.ERROR_DEFAULT_CODE;
import static com.udacity.utils.AppConstants.ERROR_DEFAULT_MSG;
import static com.udacity.utils.AppConstants.ERROR_DEFAULT_STATUS;

/**
 * Created by suyashg on 08/07/18.
 */

public class CoursesSyncAdapter extends AbstractThreadedSyncAdapter {

    public final String LOG_TAG = CoursesSyncAdapter.class.getSimpleName();

    // Interval at which to sync with the server = 60 seconds
    // Android 6 and below it will work as expected but in case of and 7 and 8 its syncing in around 10 mins
    public static final int SYNC_INTERVAL = 60;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    private CompositeDisposable mCompositeDisposable;

    public CoursesSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        AppLogger.d(LOG_TAG, "onPerformSync Called, Starting Sync.");
        this.mCompositeDisposable = new CompositeDisposable();
        AppApiHelper appDataManager = new AppApiHelper(new ApiHeader(new ApiHeader.PublicApiHeader(AppConstants.PARAM_VALUE_CONTENT_TYPE),
                new ApiHeader.ProtectedApiHeader()));
        mCompositeDisposable.add(appDataManager
                .getCourses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    AppLogger.d("Success:"+response.toString());
                    if (response != null) {
                        new AppDatabaseHelper().prepareForDBUpdate(getContext(), response.getCourses());
                    } else {
                        ApiError apiError = new ApiError(ERROR_DEFAULT_CODE, ERROR_DEFAULT_STATUS, ERROR_DEFAULT_MSG);
                        sendErrorBroadcast(apiError);
                    }
                    mCompositeDisposable.dispose();
                }, throwable -> {
                    ApiError apiError = ParsingUtils.parseError(throwable);
                    AppLogger.e(throwable.getMessage(), throwable);
                    mCompositeDisposable.dispose();
                    sendErrorBroadcast(apiError);
                }));
    }

    private void sendErrorBroadcast(ApiError apiError) {
        Intent intent = new Intent(getContext().getString(R.string.action_courses_error));
        intent.putExtra(getContext().getString(R.string.param_courses_error), apiError);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (accountManager.addAccountExplicitly(newAccount, "Asdf@123456", null)) {
                onAccountCreated(newAccount, context);
            }
        } else {
            Account[] accountsByType = accountManager.getAccountsByType(context.getString(R.string.sync_account_type));
            newAccount = accountsByType[0];
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {

        ContentResolver.setIsSyncable(newAccount, context.getString(R.string.app_name), 1);

        ContentResolver.setMasterSyncAutomatically(true);
        /*
         * Since we've created an account
         */
        CoursesSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME, newAccount);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context, newAccount);
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime, Account account) {
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    /**
     * Helper method to have the sync adapter sync immediately.
     * This can be used from class have context and wants to initiate sync
     *
     * @param context The context used to access the account service
     */
//    public static void syncImmediately(Context context) {
//        Account syncAccount = getSyncAccount(context);
//        if (ContentResolver.isSyncPending(syncAccount, context.getString(R.string.content_authority))  ||
//                ContentResolver.isSyncActive(syncAccount, context.getString(R.string.content_authority))) {
//            AppLogger.i("ContentResolver", "SyncPending, canceling");
//            ContentResolver.cancelSync(syncAccount, context.getString(R.string.content_authority));
//        }
//        Bundle bundle = new Bundle();
//        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
//        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
//        ContentResolver.requestSync(getSyncAccount(context),
//                context.getString(R.string.content_authority), bundle);
//    }

    /**
     * Helper method to have the sync adapter sync immediately using account
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context, Account newAccount) {
        if (ContentResolver.isSyncPending(newAccount, context.getString(R.string.content_authority))  ||
                ContentResolver.isSyncActive(newAccount, context.getString(R.string.content_authority))) {
            AppLogger.i("ContentResolver", "SyncPending, canceling");
            ContentResolver.cancelSync(newAccount, context.getString(R.string.content_authority));
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(newAccount,
                context.getString(R.string.content_authority), bundle);
    }
}
