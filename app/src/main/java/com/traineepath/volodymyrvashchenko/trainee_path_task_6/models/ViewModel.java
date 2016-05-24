package com.traineepath.volodymyrvashchenko.trainee_path_task_6.models;

import android.content.res.Resources;

public class ViewModel {

    private String mUrl;
    private Resources mResources;

    public ViewModel(Resources resources) {
        mResources = resources;
    }

    public void setUrl(int urlId) {
        mUrl = mResources.getString(urlId);
    }

    // In case you need to pass url via String from code
    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }
}
