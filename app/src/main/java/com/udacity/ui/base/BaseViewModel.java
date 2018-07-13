package com.udacity.ui.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.support.test.espresso.IdlingResource;

import com.udacity.data.DataManager;
import com.udacity.utils.rx.SchedulerProvider;
import com.udacity.utils.testing.SimpleIdlingResource;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by suyashg on 06/07/18.
 */

public abstract class BaseViewModel<N> extends ViewModel {

    protected SimpleIdlingResource mIdlingResource;

    private final SchedulerProvider mSchedulerProvider;

    private final DataManager mDataManager;

    private WeakReference<N> mNavigator;

    private CompositeDisposable mCompositeDisposable;

    private final ObservableBoolean isLoading = new ObservableBoolean(false);

    public BaseViewModel(SchedulerProvider mSchedulerProvider, DataManager mDataManager) {
        this.mSchedulerProvider = mSchedulerProvider;
        this.mDataManager = mDataManager;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }


    public IdlingResource getIdlingResource() {
        return mIdlingResource;
    }
}
