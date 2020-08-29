package com.softtech.android.displaydata.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BrowserSummaryInfos implements Serializable {
    private Map<String,BrowserChartInfo> chartInfo;

    public BrowserSummaryInfos() {
        this.chartInfo = new HashMap<>();
    }

    public Map<String, BrowserChartInfo> getChartInfo() {
        return chartInfo;
    }

    public void setChartInfo(Map<String, BrowserChartInfo> chartInfo) {
        this.chartInfo = chartInfo;
    }

    public float getBrowserPercentage(String browserName){
        int totalBrowserNumber = 0;
        int currentBrowserCount = 0;
        for(Map.Entry<String, BrowserChartInfo> entry : this.chartInfo.entrySet()) {
            totalBrowserNumber += entry.getValue().getBrowserCount();

            if (entry.getKey() == browserName){
                currentBrowserCount = entry.getValue().getBrowserCount();
            }
        }

        return  (currentBrowserCount / (float)totalBrowserNumber) * 100;
    }
}
