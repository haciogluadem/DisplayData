package com.softtech.android.displaydata.utils;

import android.app.Activity;
import android.content.Intent;

import com.softtech.android.displaydata.models.DataItem;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ActivityController {

    public static void startActivity(Activity context, Type type, String key1,
                                     ArrayList<DataItem> dataItems, boolean clearStack) {
        if (context == null || context.isFinishing()) return;
        Intent intent = new Intent(context, (Class<?>) type);
        intent.putExtra(key1, dataItems);

        if (clearStack)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
