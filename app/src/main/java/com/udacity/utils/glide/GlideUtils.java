package com.udacity.utils.glide;

import android.content.Context;

import com.udacity.R;

/**
 * Created by suyashg on 13/07/18.
 */

public class GlideUtils {

    public static void initConfig(Context context) {
        GlideBindingConfig.registerProvider(context.getString(R.string.glide_config1), (iv, request) ->
                request//.centerCrop()
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_placeholder)
                        .crossFade()
        );
        GlideBindingConfig.registerProvider(context.getString(R.string.glide_config2), (iv, request) ->
                request//.fitCenter()
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_placeholder)
                        .bitmapTransform(new CircularTransformation(context))
                        .crossFade()
        );
        //GlideBindingConfig.setDefault("config1");
    }
}
