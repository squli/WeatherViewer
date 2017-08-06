package ru.squel.weatherviewer.data;

import android.support.annotation.Nullable;

/**
 * Created by sq on 23.07.2017.
 */
public interface WeatherDataSource {

    void weatherForecastRequest(String place);

    void setWeatherForecastResponse(WeatherForecastResponse weatherForecastResponse);

    boolean isAvaliable(@Nullable String place);

}
