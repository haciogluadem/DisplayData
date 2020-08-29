package com.softtech.android.displaydata.models;

import java.io.Serializable;

public class BrowserChartInfo implements Serializable {
    private String browserName;
    private int browserCount;
    private int sumOfPerformance;
    private int sumOfRating;

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public int getBrowserCount() {
        return browserCount;
    }

    public void setBrowserCount(int browserCount) {
        this.browserCount = browserCount;
    }

    public int getSumOfPerformance() {
        return sumOfPerformance;
    }

    public void setSumOfPerformance(int sumOfPerformance) {
        this.sumOfPerformance = sumOfPerformance;
    }

    public int getSumOfRating() {
        return sumOfRating;
    }

    public void setSumOfRating(int sumOfRating) {
        this.sumOfRating = sumOfRating;
    }

    public float getAveragePerformance() {
        return sumOfPerformance/(float)browserCount;
    }

    public float getAverageRating() {
        return sumOfRating/(float)browserCount;
    }
}
