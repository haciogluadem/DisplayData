package com.softtech.android.displaydata.utils;

import android.graphics.Color;

import com.softtech.android.displaydata.models.BrowserChartInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ColorUtils {
    private Random rnd = new Random();
    private static ColorUtils instance;

    private Map<String, Integer> mColors;

    private ColorUtils(){
        mColors = new HashMap<>();
    }

    public static ColorUtils getInstance(){
        if(instance == null){
            instance = new ColorUtils();
        }
        return instance;
    }

    public void addColor(String key, int color){
        mColors.put(key, color);
    }

    public int getColor(String key){
        if (mColors.containsKey(key)){
            return mColors.get(key);
        }
        int color = createRandomColor();
        addColor(key, color);
        return color;
    }

    public int createRandomColor(){
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256),
                rnd.nextInt(256));
    }
}
