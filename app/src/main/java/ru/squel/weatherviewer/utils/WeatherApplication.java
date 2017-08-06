package ru.squel.weatherviewer.utils;
import android.app.Application;

/**
 * Created by sq on 22.07.2017.
 */

public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Injector.instance().init(this);
    }
}
