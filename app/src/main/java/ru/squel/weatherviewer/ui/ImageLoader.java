package ru.squel.weatherviewer.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import ru.squel.weatherviewer.data.remote.UrlCreator;

/**
 * Класс предназначен для получения картинки с симоволическим изображением погоды
 * с сервера по её идентификатору
 * Created by sq on 29.07.2017.
 */
public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

    /// вьюха для отображения иконки
    private ImageView imageView;

    /// ссылка на кэш с картинками
    private HashMap<String, Bitmap> map;

    public ImageLoader(ImageView imageView, HashMap<String, Bitmap> map) {
        this.imageView = imageView;
        this.map = map;
    }

    /// загрузка изображения
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL( (new UrlCreator()).getUrlImage(params[0]));

            // открыть объект connection, получить Input Stream
            connection = (HttpURLConnection) url.openConnection();

            try (InputStream inputStream = connection.getInputStream()) {
                bitmap = BitmapFactory.decodeStream(inputStream);
                map.put(params[0], bitmap); // Кэширование
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }

        return bitmap;
    }

    /// Связать значок погодных условий с элементом списка
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
