package com.example.lasttry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class Dashboard extends AppCompatActivity implements LocationListener {

    public static String city_name;
    DrawerLayout drawerLayout;
    LocationManager locationManager;
    EditText editText;
    Button button,btnGoToComments,btnPhotos;
    TextView temptv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        temptv = findViewById(R.id.textView2);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        findLocation(location);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FindAQI();
            }
        });

        btnGoToComments = findViewById(R.id.btnComments);
        btnPhotos = findViewById(R.id.btnPhotos);

        btnGoToComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curr_city;
                curr_city = editText.getText().toString();
                startActivity(new Intent(Dashboard.this, CommentActivity.class).putExtra("location",curr_city));
            }
        });
        btnPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curr_city;
                curr_city = editText.getText().toString();
                startActivity(new Intent(Dashboard.this, CameraPictureActivity.class).putExtra("location",curr_city));
            }
        });
    }

    public void FindAQI() {
        final String city = editText.getText().toString();
        //String url ="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=462f445106adc1d21494341838c10019&units=metric";
        String url = "https://api.waqi.info/feed/"+city+"/?token=80bd1cea7e426704f537138a29adefb7d1adcfa2";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //find temperature
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject object = jsonObject.getJSONObject("data");
                            int temp = object.getInt("aqi");
                            temptv.setText("Current AQI is - "+temp);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Dashboard.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
        requestQueue.add(stringRequest);
    }

    public void findLocation(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = null;
            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

            city_name = addresses.get(0).getLocality();
            editText.setText(city_name);
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