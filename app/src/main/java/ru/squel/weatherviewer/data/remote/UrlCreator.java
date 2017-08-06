package ru.squel.weatherviewer.data.remote;

import java.net.URLEncoder;

import ru.squel.weatherviewer.data.DataConstants;

/**
 * Created by sq on 23.07.2017.
 */
public class UrlCreator implements DataConstants {

    public UrlCreator() {
    }

    /// сформировать url
    private String formUrlforecast(String place) {
        StringBuilder builder = new StringBuilder();

        try {
            builder.append(DataConstants.SERVER_URL);
            builder.append(DataConstants.SERVER_FORECAST_URL);
            builder.append(URLEncoder.encode(place, "UTF-8"));
            builder.append("&units=");
            builder.append(SERVER_UNITS);
            builder.append("&cnt=");
            builder.append(SERVER_MAX_LINES_COUNT);
            builder.append("&APPID=");
            builder.append(SERVER_API_KEY);

            return builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String formUrlimageFet(String imgId) {
        StringBuilder builder = new StringBuilder();

        try {
            builder.append(DataConstants.SERVER_URL);
            builder.append(DataConstants.SERVER_IMAGE_PATH);
            builder.append(URLEncoder.encode(imgId, "UTF-8"));
            builder.append(DataConstants.SERVER_IMAGE_EXTENS);
            return builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /// вернуть url для прогноза
    public String getUrl(String place) {
        return formUrlforecast(place);
    }

    /// вернуть URL для загрузки картинки
    public String getUrlImage(String imgId) {
        return formUrlimageFet(imgId);
    }
}
