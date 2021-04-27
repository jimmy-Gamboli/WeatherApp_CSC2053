package com.example.thunder_weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText mLocationInput;
    private TextView mWeatherText;
    private TextView mTemperatureText;
    private TextView mWindText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationInput = (EditText) findViewById(R.id.cityInput);
        mWeatherText = (TextView) findViewById(R.id.cityInput);
        mTemperatureText = (TextView) findViewById(R.id.cityInput);
        mWindText = (TextView) findViewById(R.id.cityInput);

    }

    public void searchWeather(View view) {
        String query = mLocationInput.getText().toString();
        fetchWeatherForecast weather = new fetchWeatherForecast(mLocationInput, mWeatherText, mTemperatureText, mWindText);
        weather.execute(query);
    }
}