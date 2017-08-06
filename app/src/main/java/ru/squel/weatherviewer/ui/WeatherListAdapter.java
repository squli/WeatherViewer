package ru.squel.weatherviewer.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import ru.squel.weatherviewer.R;
import ru.squel.weatherviewer.data.WeatherForDay;

/**
 * Created by sq on 23.07.2017.
 */
public class WeatherListAdapter extends BaseAdapter {

    private ArrayList<WeatherForDay> weatherToDisplay;
    private final Context context;

    /// Кэш для сохранения иконок с отображением погоды
    private HashMap<String, Bitmap> bitmapsImages = new HashMap<>();

    /**
     * Холдер отображения одного дня погоды
     */
    private static class  ViewHolder {
        TextView date;
        TextView weather_descr;
        TextView tempDay;
        TextView tempNight;
        TextView tempEvening;
        TextView tempMorning;

        TextView pressure_hPa;
        TextView humPercent;
        ImageView icon;
        TextView windSpeedMs;
        ImageView windDirection;
        TextView cloudPercent;

        TextView rainy;
    };

    /// Конструктор для инициализации суперкласса
    public WeatherListAdapter(Context context, ArrayList<WeatherForDay> values) {
        this.context = context;
        weatherToDisplay = values;
    }

    @Override
    public int getCount() {
        return weatherToDisplay.size();
    }

    @Override
    public WeatherForDay getItem(int position) {
        return weatherToDisplay.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *  Рисует вьюху для списка
     * @param position - номер в списке
     * @param convertView элемент View, представляющий элемент ListView
     * @param parent родитель элемента ListView
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        WeatherForDay day = getItem(position);

        // Объект, содержащий ссылки на представления элемента списка
        ViewHolder viewHolder;

        // Проверить возможность повторного использования convertView для
        // элемента, за границами экрана
        if (convertView == null) {
            // нет объекта, который можно использовать заново, значит создать новый
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_item, parent, false);
            viewHolder.date = (TextView) convertView.findViewById(R.id.weather_date);
            viewHolder.weather_descr = (TextView) convertView.findViewById(R.id.weather_description);

            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.windDirection = (ImageView) convertView.findViewById(R.id.wind_pict);

            viewHolder.tempDay = (TextView) convertView.findViewById(R.id.day_temp);
            viewHolder.tempNight = (TextView) convertView.findViewById(R.id.night_temp);
            viewHolder.tempEvening = (TextView) convertView.findViewById(R.id.even_temp);
            viewHolder.tempMorning = (TextView) convertView.findViewById(R.id.morn_temp);

            viewHolder.windSpeedMs = (TextView) convertView.findViewById(R.id.wind_speed);
            viewHolder.pressure_hPa = (TextView) convertView.findViewById(R.id.pressure);

            viewHolder.humPercent = (TextView) convertView.findViewById(R.id.humidity);
            viewHolder.rainy = (TextView) convertView.findViewById(R.id.rainy);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

      // проверить наличие картинки в кэще или загрузить с сервера
        if (bitmapsImages.containsKey(day.getIcon())) {
            Bitmap bm = bitmapsImages.get(day.getIcon());
            viewHolder.icon.setImageBitmap(bm);
        }
        else
        {
            // сходить на сервер за картинкой, отобразить её и положить в кэш
            new ImageLoader(viewHolder.icon, bitmapsImages).execute(day.getIcon());
        }

        // отобразить viewHolder
        viewHolder.date.setText(day.getDate());
        viewHolder.weather_descr.setText(day.getWeather_descr());

        viewHolder.tempDay.setText(day.getTempDay());
        viewHolder.tempNight.setText(day.getTempNight());
        viewHolder.tempEvening.setText(day.getTempEven());
        viewHolder.tempMorning.setText(day.getTempMorn());

        viewHolder.windSpeedMs.setText(day.getWindSpeedMs());
        viewHolder.pressure_hPa.setText(day.getPressure_hPa());
        viewHolder.humPercent.setText(day.getHumPercent());
        viewHolder.rainy.setText(day.getRain());

        return convertView;
    }
}
