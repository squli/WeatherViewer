package ru.squel.weatherviewer.data;


import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;

import ru.squel.weatherviewer.data.local.LocalWeatherData;
import ru.squel.weatherviewer.data.remote.RemoteWeatherData;

/**
 * Created by sq on 19.07.2017.
 */
public class DataRepository implements WeatherDataSource, WeatherForecastResponse {

    private static DataRepository INSTANCE = null;

    private final WeatherDataSource mWeatherRemoteDataSource;
    private final WeatherDataSource mWeatherLocalDataSource;

    /// что-то, реализующее интерфейс коллбэка, который будет
    /// вызван после получения данных
    private WeatherForecastResponse weatherForecastResponse;

    /**
     * For use as singleton
     *
     */
    public static DataRepository getInstance(LoaderManager loaderManager) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(loaderManager);
        }
        return INSTANCE;
    }

    /**
     * To prevent direct construct
     */
    private DataRepository(LoaderManager loaderManager) {
       mWeatherRemoteDataSource = new RemoteWeatherData(loaderManager);
       mWeatherLocalDataSource = new LocalWeatherData();

    }

    /**
     * Used to force to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Получение данных из кэша, локального хранилища или удаленного хранилища
     * @return
     */
    @Override
    public void weatherForecastRequest(String place) {

        if (mWeatherLocalDataSource.isAvaliable(place)) {
            mWeatherLocalDataSource.setWeatherForecastResponse(weatherForecastResponse);
            mWeatherLocalDataSource.weatherForecastRequest(place);
        }
        else {
            if (mWeatherRemoteDataSource.isAvaliable(null)) {
                mWeatherRemoteDataSource.setWeatherForecastResponse(weatherForecastResponse);
                mWeatherRemoteDataSource.weatherForecastRequest(place);
            }
        }
    }

    @Override
    public void setWeatherForecastResponse(WeatherForecastResponse weatherForecastResponse) {
        this.weatherForecastResponse = weatherForecastResponse;
    }

    /// Проверка доступности данных в репозитории
    @Override
    public boolean isAvaliable(@Nullable String place) {
        return mWeatherLocalDataSource.isAvaliable(place) || mWeatherRemoteDataSource.isAvaliable(place);
    }

    @Override
    public void weatherForecastResponseCallback(WeatherForecast responseFromApi) {
        if (this.weatherForecastResponse != null) {
            this.weatherForecastResponse.weatherForecastResponseCallback(responseFromApi);
        }
    }

}
