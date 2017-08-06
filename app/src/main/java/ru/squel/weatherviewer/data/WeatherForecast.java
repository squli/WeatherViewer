package ru.squel.weatherviewer.data;

import java.util.ArrayList;

/**
 * Created by sq on 19.07.2017.
 */
public class WeatherForecast {

    private String place;

    ArrayList<WeatherForDay> forecast;

    public WeatherForecast() {
        forecast = new ArrayList<>();
    }

    public WeatherForecast(ArrayList<WeatherForDay> f) {
        forecast = f;
    }

    public ArrayList<WeatherForDay> getForecast() {
        return forecast;
    }

    public void add(WeatherForDay w) { forecast.add(w);}

    public void setPlace(String place) {this.place = place;}
    public String getPlace() {return place;}
}
