package com.softtech.android.displaydata.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CountrySummaryInfos implements Serializable {
    private Map<String, BrowserSummaryInfos> chartInfo;

    public CountrySummaryInfos() {
        this.chartInfo = new HashMap<>();
    }

    public Map<String, BrowserSummaryInfos> getChartInfo() {
        return chartInfo;
    }

    public void setChartInfo(Map<String, BrowserSummaryInfos> chartInfo) {
        this.chartInfo = chartInfo;
    }
}
