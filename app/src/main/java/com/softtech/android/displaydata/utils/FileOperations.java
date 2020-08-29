package com.softtech.android.displaydata.utils;

import android.content.Context;

import com.softtech.android.displaydata.R;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.RawRes;

public class FileOperations {
    public static String loadFromAsset(Context context, @RawRes int rawId){
        String json = "";
        try {
            InputStream in = context.getResources().openRawResource(rawId);

            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            json = new String(buffer);
        } catch (IOException ex) {
        }
        return json;
    }
}
