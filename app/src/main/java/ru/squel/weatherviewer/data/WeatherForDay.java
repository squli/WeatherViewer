package ru.squel.weatherviewer.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by sq on 19.07.2017.
 *
 list.dt Time of data forecasted
 list.temp
 list.temp.day              Day temperature.
 list.temp.min              Min daily temperature.
 list.temp.max              Max daily temperature.
 list.temp.night            Night temperature.
 list.temp.eve              Evening temperature.
 list.temp.morn             Morning temperature.
 list.pressure              Atmospheric pressure on the sea level, hPa
 list.humidity              Humidity, %
 list.weather               (more info Weather condition codes)
 list.weather.id            Weather condition id
 list.weather.main          Group of weather parameters (Rain, Snow, Extreme etc.)
 list.weather.description   Weather condition within the group
 list.weather.icon          Weather icon id
 list.speed                 Wind speed.
 list.deg                   Wind direction, degrees (meteorological)
 list.clouds                Cloudiness, %
 list.rain

 */
public class WeatherForDay {

    private long dt;        // Временная метка даты/времени
    private double tempMin;
    private double tempMax;
    private double tempDay;
    private double tempNight;
    private double tempEvening;
    private double tempMorning;
    private double pressure_hPa;
    private double humPercent;
    private int weather_id;
    private String weather_main;
    private String weather_descr;
    private String icon;
    private double windSpeedMs;
    private int windDirection;
    private double cloudPercent;
    private double rain;

    /// Адовый конструктор
    public WeatherForDay(long dt,
                         double tempMin,
                         double tempMax,
                         double tempDay,
                         double tempNight,
                         double tempEvening,
                         double tempMorning,
                         double pressure_hPa,
                         double humPercent,
                         int weather_id,
                         String weather_main,
                         String weather_descr,
                         String icon,
                         double windSpeedMs,
                         int windDirection,
                         double cloudPercent) {
        this.dt = dt;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.tempDay = tempDay;
        this.tempNight = tempNight;
        this.tempEvening = tempEvening;
        this.tempMorning = tempMorning;
        this.pressure_hPa = pressure_hPa;
        this.humPercent = humPercent;
        this.weather_id = weather_id;
        this.weather_main = weather_main;
        this.weather_descr = weather_descr;
        this.icon = icon;
        this.windSpeedMs = windSpeedMs;
        this.windDirection = windDirection;
        this.cloudPercent = cloudPercent;
    }

    public String getDate() {
        Date date = new Date(this.dt * 1000);
        DateFormat df = new SimpleDateFormat("dd MM yyyy");
        return df.format(date);
    }

    public String getTempMax() {
        return Objects.toString(tempMax, null) + "°C";
    }

    public String getTempMin() {
        return Objects.toString(tempMin, null) + "°C";
    }

    public String getTempDay() {
        return String.format("%.1f °C", tempDay);
    }

    public String getTempNight() {
        return String.format("%.1f °C", tempNight);
    }

    public String getTempMorn() {
        return String.format("%.1f °C", tempMorning);
    }

    public String getTempEven() {
        return String.format("%.1f °C", tempEvening);
    }

    public String getPressure_hPa() {
        return String.format("%.1f kPa", pressure_hPa);
    }

    public String getHumPercent() {
        return Objects.toString(humPercent, null) + "%";
    }

    public String getWeather_id() {
        return Objects.toString(weather_id, null);
    }

    public String getWeather_main() {
        return weather_main;
    }

    public  String getWeather_descr() {
        return weather_descr;
    }

    public String getIcon() {
        String s = "";
        try {
            s = icon.substring(0, 3);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public String getWindSpeedMs() {
        return Objects.toString(windSpeedMs, null) + "m/s";
    }

    public String getWindDirection() {
        return Objects.toString(windDirection, null);
    }

    public String getCloudPercent() {
        return Objects.toString(cloudPercent, null);
    }

    public String getRain() {
        return Objects.toString(rain, null) + "mm";
    }

    public void setRain (double rain) {
        this.rain = rain;
    }
}
