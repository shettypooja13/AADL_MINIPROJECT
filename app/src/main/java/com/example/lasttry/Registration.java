package com.example.lasttry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {
    DatabaseHelper1 db;
    private EditText Username;
    private EditText Password;
    private EditText confirm_password;
    private Button Signup;
    private final String CREDENTIAL_SHARED_PREF = "our_shared_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        db = new DatabaseHelper1(this);

        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        confirm_password = findViewById(R.id.confirm_password);
        Signup = findViewById(R.id.Signup);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pwd = Password.getText().toString();
                String cnf_pwd = confirm_password.getText().toString();
                String user = Username.getText().toString();

                if(pwd.equals(cnf_pwd)){
                    long val = db.addUser(user,pwd);
                    if(val > 0){
                        Toast.makeText(Registration.this,"You have registered",Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(Registration.this,Login.class);
                        startActivity(moveToLogin);
                    }
                    else{
                        Toast.makeText(Registration.this,"Registeration Error",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(Registration.this,"Password is not matching",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}