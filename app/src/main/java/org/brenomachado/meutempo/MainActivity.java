package org.brenomachado.meutempo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        String[] forecastList = {
                "Hoje - Ensolarado - 24 / 18",
                "Amanhã - Ensolarado - 24 / 18",
                "Sex - Ensolarado - 24 / 18",
                "Sab - Ensolarado - 24 / 18",
                "Dom - Ensolarado - 24 / 18",
                "Seg - Ensolarado - 24 / 18"
        };

        ArrayAdapter<String> mForecastAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, forecastList);

        ListView listView = (ListView) findViewById(R.id.listview_forecast);

        listView.setAdapter(mForecastAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_refresh:
                FetchWeatherTask task = new FetchWeatherTask();
                task.execute("3152100");

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

