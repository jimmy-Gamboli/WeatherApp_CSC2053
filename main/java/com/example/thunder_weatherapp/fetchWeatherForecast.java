package com.example.thunder_weatherapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class fetchWeatherForecast  extends AsyncTask<String, Void, String> {
    //References to text view of weather, location, temperature, and wind
    private WeakReference<TextView> mWeatherText;
    private WeakReference<TextView> mWindText;
    private WeakReference<TextView> mLocationText;
    private WeakReference<TextView> mTemperatureText;


    fetchWeatherForecast(){
        //Initialize variables for each of the textviews
       /* mLocationText = new WeakReference<>(locationText);
        mWeatherText = new WeakReference<>(tempText);
        mTemperatureText = new WeakReference<>(weatherText);
        mWindText = new WeakReference<>(windText);*/
    }

    @Override
    protected String doInBackground(String... strings) {
        String weatherForcast=null;
        try{

            weatherForcast=getWeather(strings[0]);
        } catch(IOException e){
            e.printStackTrace();
        }
        return weatherForcast;
    }
    ///Uses woeid to connect to another version of API that actually provides weather data
    private String getWeather(String location) throws IOException {

        String forecast = null;
        String id =getWOEID(location);
       // String url = "https://goweather.herokuapp.com/weather/"+"Curita";
        String url ="https://www.metaweather.com/api/location/"+id;
        Log.d("GETweatherTag",url);

        URL requestURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        InputStream input = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        StringBuilder builder = new StringBuilder();
        String line;
        while((line = reader.readLine())!=null){
            builder.append(line);
            builder.append("\n");
        }
        forecast = builder.toString();
        Log.d("GETweatherTag",forecast);



        return forecast;

    }
    ////Gets woeid(weather ID) which is necessary in order to search for the weather for the location
    protected String getWOEID(String s) throws IOException{
        String forecast = null;
        String id=null;

        String url ="https://www.metaweather.com/api/location/search/?query="+s;
        Log.d("GETweatherTag",url);

        URL requestURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        InputStream input = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        StringBuilder builder = new StringBuilder();
        String line;
        while((line = reader.readLine())!=null){
            builder.append(line);
            builder.append("\n");
        }
        forecast = builder.toString();

        try {
            Log.d("GETweatherTag","INSIDE OF TRY CAtCH");
            Log.d("GETweatherTag",forecast);
            JSONArray array = new JSONArray(forecast);

            JSONObject lol = array.getJSONObject(0);
            id  = lol.getString("woeid");
            Log.d("GETweatherTag", s);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        
    }



}
