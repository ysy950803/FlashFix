package com.ysy.movieguide.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SpDataUtils {

    private static SpDataUtils sInstance = null;

    public static SpDataUtils getsInstance(Context context) {
        if (sInstance == null)
            return new SpDataUtils(context);
        return sInstance;
    }

    private Context mContext;

    public SpDataUtils(Context context) {
        mContext = context.getApplicationContext();
    }

    public void saveData(String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getData(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sp.getBoolean(key, true);
    }
}
