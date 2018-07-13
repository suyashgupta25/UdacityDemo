package com.udacity.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by suyashg on 06/07/18.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(int position);
}
