package com.udacity.utils.glide;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by suyashg on 12/07/18.
 */

public class GlideBindingConfig {
    public interface Provider {
        @NonNull
        DrawableRequestBuilder<?> provide(ImageView target, DrawableTypeRequest<?> request);
    }

    private static Map<Object, Provider> providers = new HashMap<>();
    private static Provider defaultProvider = null;

    static Provider getProvider(Object key) {
        return providers.get(key);
    }

    static Provider getDefaultProvider() {
        return defaultProvider;
    }

    public static void registerProvider(Object key, Provider provider) {
        providers.put(key, provider);
    }

    public static void setDefault(Object key) {
        defaultProvider = providers.get(key);
    }

}
