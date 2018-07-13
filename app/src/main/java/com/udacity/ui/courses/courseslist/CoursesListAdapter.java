package com.udacity.ui.courses.courseslist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.udacity.R;
import com.udacity.databinding.ItemCourseListBinding;
import com.udacity.databinding.ItemCoursesEmptyViewBinding;
import com.udacity.ui.base.BaseViewHolder;
import com.udacity.utils.AppLogger;

/**
 * Created by suyashg on 06/07/18.
 */

public class CoursesListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String TAG = CoursesListAdapter.class.getSimpleName();

    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_EMPTY = 1;
    private static final int COUNT_TYPE_EMPTY = 1;

    private CoursesListAdapterListener mListener;

    private Cursor mCursor;

    private String mErrorMsg;

    public CoursesListAdapter(CoursesListAdapterListener mListener) {
        this.mListener = mListener;
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public void clearItemsForError(String errorMsg) {
        this.mErrorMsg = errorMsg;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (null != mCursor && mCursor.getCount() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemCourseListBinding itemCourseListBinding = ItemCourseListBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new CourseViewHolder(itemCourseListBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemCoursesEmptyViewBinding emptyViewBinding = ItemCoursesEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (null == mCursor || mCursor.getCount() == 0) {
            return COUNT_TYPE_EMPTY;
        }
        return mCursor.getCount();
    }

    public class CourseViewHolder extends BaseViewHolder implements CoursesListItemViewModel.CourseItemViewModelListener {
        private ItemCourseListBinding mBinding;
        private CoursesListItemViewModel mCoursesListItemViewModel;

        public CourseViewHolder(ItemCourseListBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mCursor.moveToPosition(position);
            mCoursesListItemViewModel = new CoursesListItemViewModel(mCursor, this);
            mBinding.setViewModel(mCoursesListItemViewModel);
            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            //mBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(String key) {
            AppLogger.d(TAG, "onItemClick=" + key);
            if (mListener != null) {
                mListener.onCourseClicked(key);
            }
        }
    }

    public class EmptyViewHolder extends BaseViewHolder implements CoursesListEmptyItemViewModel.CoursesEmptyItemViewModelListener {

        private ItemCoursesEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemCoursesEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            CoursesListEmptyItemViewModel emptyItemViewModel = new CoursesListEmptyItemViewModel(this, mErrorMsg);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onRetryClick() {
            if (mListener != null) {
                mListener.onRetryClick();
            }
        }
    }

    public interface CoursesListAdapterListener {

        void onRetryClick();

        void onCourseClicked(String key);
    }
}
