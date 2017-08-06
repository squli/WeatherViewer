package ru.squel.weatherviewer.presenter;

import ru.squel.weatherviewer.BasePresenter;
import ru.squel.weatherviewer.BaseView;
import ru.squel.weatherviewer.data.WeatherForecast;

/**
 * Created by sq on 23.07.2017.
 */
public interface ViewPresenterContract {
    interface View extends BaseView<Presenter> {
        void displayForecast(WeatherForecast forecast);
        void displayToastNeedInternet();
    }

    interface Presenter extends BasePresenter {
        void getNewForecast(String place);
    }
}
