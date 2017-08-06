package ru.squel.weatherviewer.ui;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.squel.weatherviewer.data.WeatherForecast;

/**
 * A placeholder fragment containing a simple view.
 */
public class WeatherListFragment extends ListFragment {

    private static final String LOG_TAG = WeatherListFragment.class.getSimpleName();

    /// здесь хранится текущий прогноз
    private WeatherForecast currentForecast;

    /// пустой конструктор для любого фрагмента
    public WeatherListFragment() {
    }

    /// заполнение данными прогноза
    //TODO вынести логику в презентер
    public void setCurrentForecast(WeatherForecast w) {
        currentForecast = w;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (currentForecast != null) {
            WeatherListAdapter adapter = new WeatherListAdapter(getContext(), currentForecast.getForecast());
            setListAdapter(adapter);
        }

        Log.d(LOG_TAG, "onCreateView");

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
