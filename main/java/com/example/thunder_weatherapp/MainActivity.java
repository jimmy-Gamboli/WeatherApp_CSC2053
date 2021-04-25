package com.example.thunder_weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mLocationInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationInput = (EditText) findViewById(R.id.cityInput);
    }


    public void searchWeather(View view) {
        String query = mLocationInput.getText().toString();
        fetchWeatherForecast weather = new fetchWeatherForecast();
        weather.execute(query);
    }
}