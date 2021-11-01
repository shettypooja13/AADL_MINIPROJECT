package com.example.lasttry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private Button signup;
    private final String CREDENTIAL_SHARED_PREF = "our_shared_pref";
    DatabaseHelper1 db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*Dialog dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
        View v = this.getLayoutInflater().inflate(R.layout.progress_bar,null);
        dialog.setContentView(v);
        dialog.show();*/

        db = new com.example.lasttry.DatabaseHelper1(this);


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pwd = password.getText().toString();
                Boolean res = db.checkUser(user, pwd);
                if(res == true)
                {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Login.this,"Login Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}