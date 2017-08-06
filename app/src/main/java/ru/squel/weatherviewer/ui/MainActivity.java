package ru.squel.weatherviewer.ui;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import ru.squel.weatherviewer.R;
import ru.squel.weatherviewer.data.DataRepository;
import ru.squel.weatherviewer.data.WeatherForecast;
import ru.squel.weatherviewer.presenter.MainActivityPresenter;
import ru.squel.weatherviewer.presenter.ViewPresenterContract;

public class MainActivity extends AppCompatActivity implements ViewPresenterContract.View {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /// строка для отображения
    private String lastPlace;

    private ViewPresenterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // первый запуск
        if (presenter == null) {
            setPresenter(new MainActivityPresenter(
                                DataRepository.getInstance(getSupportLoaderManager()),
                                this));
        }

        // восстановление предъидущего состояния при восстановлении активити
        if (savedInstanceState != null) {
            String lastTimeString = savedInstanceState.getString("lastPlace");
            TextView inputPlace = (TextView)findViewById(R.id.editCity);
            presenter.getNewForecast(lastTimeString);
            inputPlace.setText(lastTimeString);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        final TextView inputPlace = (TextView)findViewById(R.id.editCity);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissKeyboard(view);
                    lastPlace = inputPlace.getText().toString();
                    presenter.getNewForecast(lastPlace);
                }
            });
        }
    }

    /**
     * Отображение погоды в фраменте списка через отправку транзации
     * @param forecast
     */
    @Override
    public void displayForecast(WeatherForecast forecast) {
        // создан новый фрагмент
        WeatherListFragment fragment = new WeatherListFragment();
        // установить id для отображения в новом фрагменте
        fragment.setCurrentForecast(forecast);
        // создать новую тразакцию
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // замена фрейма fragment_container на созданный фрагмент
        transaction.replace(R.id.fragment_container, fragment);
        // запуск транзакции
        transaction.addToBackStack(null);
        // настройка анимации при пересоздании фрагмента
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        // транзакция отправлена
        transaction.commit();

        Log.d(LOG_TAG, "displayForecast");
    }

    @Override
    public void setPresenter(ViewPresenterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onSaveInstanceState(Bundle in) {
        super.onSaveInstanceState(in);
        in.putString("lastPlace", lastPlace);
    }

    // Клавиатура закрывается при касании кнопки FAB
    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    public void displayToastNeedInternet() {
        Toast.makeText(this, getResources().getString(R.string.error_message),
                Toast.LENGTH_LONG).show();
    }
}
