package ru.squel.weatherviewer.data;

/**
 * Created by sq on 23.07.2017.
 */
public interface DataConstants {
    String SERVER_URL = "http://api.openweathermap.org";
    String SERVER_FORECAST_URL = "/data/2.5/forecast/daily?q=";
    String SERVER_IMAGE_PATH = "/img/w/";
    String SERVER_IMAGE_EXTENS = ".png";
    String SERVER_UNITS = "metric";
    String SERVER_MAX_LINES_COUNT = "16";
    String SERVER_API_KEY = "b3bf7e65fbdc14248db1d1458c4862c2";

    String SHARED_PREF_NAME = "LAST_REQUEST";
    String SHARED_PREF_TIME = "LAST_REQUEST_TIME";
    String SHARED_PREF_PLACE = "LAST_REQUEST_PLACE";
}
