package com.example.lasttry;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    DrawerLayout drawerLayout;
    Button button;
    EditText editText;
    ImageView imageView;
    TextView temp, time, longitude, latitude, humidity, describe, sunrise, sunset, pressure, wind, country, city_nam, max_temp, min_temp, feels;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        drawerLayout = findViewById(R.id.drawer_layout);
        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        temp = findViewById(R.id.textView3);
        time = findViewById(R.id.textView2);
        describe = findViewById(R.id.describe);
        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
        humidity = findViewById(R.id.humidity);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        pressure = findViewById(R.id.pressure);
        wind = findViewById(R.id.wind);
        country = findViewById(R.id.country);
        city_nam = findViewById(R.id.city_nam);
        max_temp = findViewById(R.id.temp_max);
        min_temp = findViewById(R.id.min_temp);
        feels = findViewById(R.id.feels);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        findLocation2(location);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FindWeather();
            }
        });
}

    public void FindWeather() {
        final String city = editText.getText().toString();
        String url ="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=462f445106adc1d21494341838c10019&units=metric";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //find temperature
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject object = jsonObject.getJSONObject("main");
                            double tempe = object.getDouble("temp");
                            temp.setText("Temperature\n"+tempe+"°C");

                            //find country
                            JSONObject object8 = jsonObject.getJSONObject("sys");
                            String count = object8.getString("country");
                            country.setText(count+"  :");

                            //find city
                            String city = jsonObject.getString("name");
                            city_nam.setText(city);

                            //find icon
                            JSONArray jsonArray = jsonObject.getJSONArray("weather");
                            JSONObject obj = jsonArray.getJSONObject(0);
                            String icon = obj.getString("icon");
                            Picasso.get().load("https://openweathermap.org/img/wn/"+icon+"@2x.png").into(imageView);

                            //find description
                            String description = obj.getString("description");
                            describe.setText(description);

                            //find date & time
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat std = new SimpleDateFormat("HH:mm a \nE, MMM dd yyyy");
                            String date = std.format(calendar.getTime());
                            time.setText(date);

                            //find latitude
                            JSONObject object2 = jsonObject.getJSONObject("coord");
                            double lat_find = object2.getDouble("lat");
                            latitude.setText(lat_find+"°  N");

                            //find longitude
                            JSONObject object3 = jsonObject.getJSONObject("coord");
                            double long_find = object3.getDouble("lon");
                            longitude.setText(long_find+"°  E");

                            //find humidity
                            JSONObject object4 = jsonObject.getJSONObject("main");
                            int humidity_find = object4.getInt("humidity");
                            humidity.setText(humidity_find+"  %");

                            //find sunrise
                            JSONObject object5 = jsonObject.getJSONObject("sys");
                            String sunrise_find = object5.getString("sunrise");
                            sunrise.setText(sunrise_find+"  am");

                            //find sunrise
                            JSONObject object6 = jsonObject.getJSONObject("sys");
                            String sunset_find = object6.getString("sunset");
                            sunset.setText(sunset_find+"  pm");

                            //find pressure
                            JSONObject object7 = jsonObject.getJSONObject("main");
                            String pressure_find = object7.getString("pressure");
                            pressure.setText(pressure_find+"  hPa");

                            //find wind speed
                            JSONObject object9 = jsonObject.getJSONObject("wind");
                            String wind_find = object9.getString("speed");
                            wind.setText(wind_find+"  km/h");

                            //find min temperature
                            JSONObject object10 = jsonObject.getJSONObject("main");
                            double mintemp = object10.getDouble("temp_min");
                            min_temp.setText("Min Temp\n"+mintemp+" °C");

                            //find max temperature
                            JSONObject object12 = jsonObject.getJSONObject("main");
                            double maxtemp = object12.getDouble("temp_max");
                            max_temp.setText("Max Temp\n"+maxtemp+" °C");

                            //find feels
                            JSONObject object13 = jsonObject.getJSONObject("main");
                            double feels_find = object13.getDouble("feels_like");
                            feels.setText(feels_find+" °C");



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WeatherActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(WeatherActivity.this);
        requestQueue.add(stringRequest);
    }

    public void findLocation2(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = null;
            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

            String city = addresses.get(0).getLocality();
            editText.setText(city);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error:" + e, Toast.LENGTH_SHORT).show();
        }
    }


    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);

    }
    public void ClickLogo(View view){
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        MainActivity.redirectActivity(this,MainActivity.class);
    }
    public void ClickDashboard(View view){
        recreate();
    }
    public void ClickAboutUs(View view){
        MainActivity.redirectActivity(this,AboutUs.class);
    }
    public void ClickLogout(View view){
        MainActivity.logout(this);
    }
    protected void onPause(){
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}
