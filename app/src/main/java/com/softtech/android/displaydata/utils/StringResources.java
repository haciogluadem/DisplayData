package com.softtech.android.displaydata.utils;

import android.content.Context;
import android.support.annotation.StringRes;

import com.softtech.android.displaydata.app.AppSingleton;

public class StringResources {
    public static String loadString(@StringRes int id) {
        return loadString(AppSingleton.getContext(), id);
    }

    public static String loadString(Context context, @StringRes int id) {
        if (context != null) {
            return context.getResources().getString(id);
        }
        return "";
    }
}
