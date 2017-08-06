package ru.squel.weatherviewer.presenter;

import android.util.Log;

import ru.squel.weatherviewer.data.DataRepository;
import ru.squel.weatherviewer.data.WeatherForecast;
import ru.squel.weatherviewer.data.WeatherForecastResponse;

/**
 * Created by sq on 19.07.2017.
 */
public class MainActivityPresenter implements ViewPresenterContract.Presenter, WeatherForecastResponse {

    private static final String LOG_TAG = MainActivityPresenter.class.getSimpleName();

    /// View для отображения этого презентера
    private ViewPresenterContract.View mView;

    /// хранилище с данными
    private DataRepository dataRepository;

    /// конструктор
    public MainActivityPresenter(DataRepository dataRepository,
                                 ViewPresenterContract.View WeatherListFragment)
    {
        this.dataRepository = dataRepository;
        mView = WeatherListFragment;
    }

    /**
     * Возвращает прогноз погоды для отображения
     * @return
     */
    public void getNewForecast(String place) {
        dataRepository.setWeatherForecastResponse(this);

        if (dataRepository.isAvaliable(place))
            dataRepository.weatherForecastRequest(place);
        else
            mView.displayToastNeedInternet();
    }

    public void start() {

    }

    /**
     * Функция отображения прогноза погоды, которая будет как Callback
     * передана на самый низ
     * @param responseFromApi
     */
    @Override
    public void weatherForecastResponseCallback(WeatherForecast responseFromApi) {

        Log.d(LOG_TAG, "weatherForecastResponse");
        mView.displayForecast(responseFromApi);
    }
}
