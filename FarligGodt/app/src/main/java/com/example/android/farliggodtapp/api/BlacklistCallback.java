package com.example.android.farliggodtapp.api;

/**
 * Created by Agne Ødegaard on 06/12/2016.
 */

public interface BlacklistCallback {

    void blacklistSuccess(Specie[] species);

    void blacklistFailed(Exception exc);

}
