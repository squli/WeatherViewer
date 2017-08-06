package ru.squel.weatherviewer.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by sq on 22.07.2017.
 */

public class Injector {

    private Application application;

    private static final Injector INSTANCE = new Injector();

    void init(Application application) {
        this.application = application;
    }

    public static Injector instance()
    {
        return INSTANCE;
    }

    public Context getAppContext() {
        return this.application;
    }
}
