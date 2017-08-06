package ru.squel.weatherviewer.data.remote;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sq on 23.07.2017.
 * Асинхронное получение ответа на запрос погоды по названию населенного пункта
 */
public class RemoteDataLoader extends AsyncTaskLoader<String> {

    public static final String LOG_TAG = RemoteDataLoader.class.getSimpleName();

    /// метка для сохранения url в bundle для передачи лоадеру
    public static final String ARG_URL = "ARG_URL";

    /// url для получения данных
    private String mUrl;

    ///
    public RemoteDataLoader(Context context, Bundle bundle) {
        super(context);
        if (bundle != null){
            mUrl = bundle.getString(ARG_URL);
        }
    }

    @Override
    public void forceLoad() {
        Log.d(LOG_TAG, "forceLoad");
        super.forceLoad();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(LOG_TAG, "onStartLoading");
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.d(LOG_TAG, "onStopLoading");
    }

    @Override
    public void deliverResult(String data) {
        Log.d(LOG_TAG, "deliverResult");
        super.deliverResult(data);
    }

    @Override
    public String loadInBackground() {
        String s = getDataFromUrl(mUrl);
        Log.d(LOG_TAG, "loadInBackground " + s);
        return s;
    }

    /**
     * Получение ответа на запрос сервера с погодой в виде строки по урлу
     * @param url
     * @return
     */
    private String getDataFromUrl(String url) {

        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection)(new URL(url)).openConnection();
            int response = connection.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                StringBuilder builder = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {

                        String line;

                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

                return builder.toString();
            }
            else
            {
                // ошибка подключения по HTTP
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            connection.disconnect();
        }
    }
}
