package com.softtech.android.displaydata.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ComputedBrowser implements Serializable {
    @SerializedName("Browser")
    private String browser;
    @SerializedName("Version")
    private String version;
    @SerializedName("Platform")
    private String platform;

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
