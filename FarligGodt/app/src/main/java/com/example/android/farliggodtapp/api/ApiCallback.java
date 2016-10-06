package com.example.android.farliggodtapp.api;

/**
 * Created by orangee on 06/10/16.
 */

public interface ApiCallback {

    void serviceSuccess(Taxon taxon);

    void serviceFailed(Exception exc);


}
