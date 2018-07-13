package com.udacity.ui.courses.courseslist;

import android.databinding.ObservableField;

/**
 * Created by suyashg on 08/07/18.
 */

public class CoursesListEmptyItemViewModel {

    private CoursesEmptyItemViewModelListener mListener;
    public final ObservableField<String> errorMsg;

    public CoursesListEmptyItemViewModel(CoursesEmptyItemViewModelListener listener, String errorMsg) {
        this.mListener = listener;
        this.errorMsg = new ObservableField<>(errorMsg);
    }

    public void onRetryClick() {
        mListener.onRetryClick();
    }

    public interface CoursesEmptyItemViewModelListener {

        void onRetryClick();
    }
}
