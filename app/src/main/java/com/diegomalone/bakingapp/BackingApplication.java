package com.diegomalone.bakingapp;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Diego Malone on 07/01/18.
 */

public class BackingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
