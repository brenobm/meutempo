package org.brenomachado.meutempo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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


}
