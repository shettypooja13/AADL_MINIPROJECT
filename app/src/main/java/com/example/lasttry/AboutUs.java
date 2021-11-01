package com.example.lasttry;

import static com.example.lasttry.Dashboard.city_name;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView tV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


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
        MainActivity.redirectActivity(this,Dashboard.class);

    }
    public void ClickAboutUs(View view){
        recreate();
    }
    public void ClickLogout(View view){
        MainActivity.logout(this);
    }
    protected void onPause(){
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }

}
