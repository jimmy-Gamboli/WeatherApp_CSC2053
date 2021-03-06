package com.example.thunder_weatherapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
    private String location;


    fetchWeatherForecast(TextView tempText, TextView weatherText, TextView windText, TextView mLocationText){
        //Initialize variables for each of the textviews


       this.mLocationText = new WeakReference<>(mLocationText);
       this.mWeatherText = new WeakReference<>(tempText);
       this.mTemperatureText = new WeakReference<>(weatherText);
       this.mWindText = new WeakReference<>(windText);
        location = null;
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
        String id = getWOEID(location);

       // String url = "https://goweather.herokuapp.com/weather/"+"Curita";
        String url ="https://www.metaweather.com/api/location/"+id;


        // Make Connection to API
        URL requestURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        // Receive Response
        InputStream input = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        // Creates a string with the response
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
        location =s;
        String url ="https://www.metaweather.com/api/location/search/?query="+s;


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

            JSONArray array = new JSONArray(forecast);

            JSONObject lol = array.getJSONObject(0);
            id  = lol.getString("woeid");



        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String weatherDescription =null;
        Double temperature =null;
        String wind =null;
        JSONObject jsonString =null;
        JSONArray weatherArr = null;


    ///Extracting information from the JSON array and strinbuilder to get weather, temperature,
        try {
            jsonString= new JSONObject(s);
            weatherArr =  jsonString.getJSONArray("consolidated_weather");


            JSONObject pair = weatherArr.getJSONObject(0);
            weatherDescription = pair.getString("weather_state_name");

            wind =pair.getString("wind_speed");

            String temporary = pair.getString("the_temp");
            Log.d("HELLOWORLD",wind);



            temperature = (1.8*Double.parseDouble(pair.getString("the_temp")))+32;

            if (weatherDescription != null && temperature != null && wind != null) {
                wind = wind.substring(0,4) +" mph "+pair.getString("wind_direction_compass");
                temperature = (1.8*Double.parseDouble(pair.getString("the_temp")))+32;

                mWeatherText.get().setText(weatherDescription);
                char degree=0xB0;

                mTemperatureText.get().setText(Double.toString(temperature)+degree+"F");
                mWindText.get().setText(wind);
                mLocationText.get().setText("Weather Forecast for: "+location);
            } else {
                mTemperatureText.get().setText("");
                mWeatherText.get().setText("NoResults");
                mWindText.get().setText("");
            }
           


        } catch (JSONException e) {
            mTemperatureText.get().setText("");
            mWeatherText.get().setText("NoResults");
            mWindText.get().setText("");
            e.printStackTrace();
        }

        }


    }











