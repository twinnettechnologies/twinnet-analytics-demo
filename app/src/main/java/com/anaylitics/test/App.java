package com.anaylitics.test;

import android.app.Application;

import com.twinnet_analytics.event.Analytics;

public class App extends Application {

    private static Application application;
    public static void init() {
        Analytics.Companion.initialize(application,"K2x2zhAkYxOWevlShPboZqLqz5OLjfw","twinnet_analytics_demo",false);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        init();
    }
}
