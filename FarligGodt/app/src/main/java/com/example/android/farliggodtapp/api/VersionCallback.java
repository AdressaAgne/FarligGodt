package com.example.android.farliggodtapp.api;

/**
 * Created by Agne Ødegaard on 06/12/2016.
 */

public interface VersionCallback {

    void onOldVersion(String version);
    void onUptoDateVersion();
    void versionAPIFailed(Exception exc);
    void versionAPISuccess(String version);

}
