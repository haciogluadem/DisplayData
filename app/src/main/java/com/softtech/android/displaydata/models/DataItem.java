package com.softtech.android.displaydata.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataItem implements Serializable {
    @SerializedName("computed_browser")
    private ComputedBrowser computedBrowser;
    @SerializedName("computed_location")
    private String computedLocation;
    private int performance;
    private int rating;
    private List<String> labels;
    private GeoLocation geo;

    public DataItem(){
        labels = new ArrayList<>();
    }

    public ComputedBrowser getComputedBrowser() {
        return computedBrowser;
    }

    public void setComputedBrowser(ComputedBrowser computedBrowser) {
        this.computedBrowser = computedBrowser;
    }

    public String getComputedLocation() {
        return computedLocation;
    }

    public void setComputedLocation(String computedLocation) {
        this.computedLocation = computedLocation;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public GeoLocation getGeo() {
        return geo;
    }

    public void setGeo(GeoLocation geo) {
        this.geo = geo;
    }
}
