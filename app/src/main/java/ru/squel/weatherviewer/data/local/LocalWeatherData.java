package ru.squel.weatherviewer.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.squel.weatherviewer.data.DataConstants;
import ru.squel.weatherviewer.data.WeatherDataSource;
import ru.squel.weatherviewer.data.WeatherForDay;
import ru.squel.weatherviewer.data.WeatherForecast;
import ru.squel.weatherviewer.data.WeatherForecastResponse;
import ru.squel.weatherviewer.utils.Injector;

/**
 * Created by sq on 23.07.2017.
 */
public class LocalWeatherData implements WeatherDataSource {

    private final String LOG_TAG = LocalWeatherData.class.getSimpleName();

    private String lastRequestPlace = "";
    private String lastRequestTime = "";
    private String lastRequestStr = "";
    private long lastRequestTimeLong = 0;
    private long timeToLoadLocalms = 10000;

    /// прогноз погоды
    private WeatherForecast weatherForecast = null;

    /// что-то, реализующее интерфейс коллбэка, который будет
    /// вызван после получения данных
    private WeatherForecastResponse weatherForecastResponseCallback;

    @Override
    public void weatherForecastRequest(String place) {
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(lastRequestStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        convertJSONtoWeatherForecast(jsonObj);

        Log.d(LOG_TAG, lastRequestStr);

        weatherForecastResponseCallback.weatherForecastResponseCallback(weatherForecast);
    }

    @Override
    public void setWeatherForecastResponse(WeatherForecastResponse weatherForecastResponse) {
        this.weatherForecastResponseCallback = weatherForecastResponse;
    }

    public boolean isAvaliable(@Nullable String place) {
        SharedPreferences sPref = Injector.instance().getAppContext().getSharedPreferences(DataConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        long currentTime = System.currentTimeMillis();

        try {
            lastRequestPlace = sPref.getString(DataConstants.SHARED_PREF_PLACE, null);
            lastRequestTime = sPref.getString(DataConstants.SHARED_PREF_TIME, null);
            lastRequestStr = sPref.getString(DataConstants.SHARED_PREF_NAME, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (lastRequestTime != null)
            lastRequestTimeLong = Long.parseLong(lastRequestTime);

        long delta = currentTime - lastRequestTimeLong;

        if (place.equalsIgnoreCase(lastRequestPlace) && (delta < timeToLoadLocalms) && (lastRequestStr != null)) {
            Log.d(LOG_TAG, "load from local");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Преобразовать JSON в прогноз погоды
     * @param jsonObject
     */
    private void convertJSONtoWeatherForecast (JSONObject jsonObject) {
        this.weatherForecast = new WeatherForecast();

        try {
            // Получение свойства "City" JSONArray
            JSONObject city = jsonObject.getJSONObject("city");
            this.weatherForecast.setPlace(city.getString("name"));

            // Получение свойства "list" JSONArray
            JSONArray list = jsonObject.getJSONArray("list");

            // Преобразовать каждый элемент списка в объект типа WeatherForDay
            for (int i = 0; i < list.length(); i++) {
                // данные за один день
                JSONObject day = list.getJSONObject(i);

                // Получить JSONObject с температурами дня ("temp")
                JSONObject temperatures = day.getJSONObject("temp");

                // Получить JSONObject c описанием и значком ("weather")
                JSONObject weather = day.getJSONArray("weather").getJSONObject(0);

                // Добавить новый объект Weather в weatherList
                WeatherForDay newDay = new WeatherForDay(
                        day.getLong("dt"),                  // Временная метка даты/времени
                        temperatures.getDouble("min"),
                        temperatures.getDouble("max"),
                        temperatures.getDouble("day"),
                        temperatures.getDouble("night"),
                        temperatures.getDouble("eve"),
                        temperatures.getDouble("morn"),
                        day.getDouble("pressure"),
                        day.getDouble("humidity"),
                        weather.getInt("id"),
                        weather.getString("main"),
                        weather.getString("description"),
                        weather.getString("icon"),
                        day.getDouble("speed"),
                        day.getInt("deg"),
                        day.getDouble("clouds"));
                try {
                    newDay.setRain(day.getDouble("rain"));
                }
                catch (Exception e) {

                }

                weatherForecast.add(newDay);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
