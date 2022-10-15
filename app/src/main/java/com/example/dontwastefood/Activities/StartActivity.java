package com.example.dontwastefood.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dontwastefood.R;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    Button login,register;
    ProgressBar progressBar;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login = findViewById(R.id.btn_to_login);
        register = findViewById(R.id.btn_to_register);
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar_start);
        progressBar.setVisibility(View.GONE);

        if(auth.getCurrentUser() != null){
            progressBar.setVisibility(View.VISIBLE);
           startActivity(new Intent(StartActivity.this, MainActivity.class));
            Toast.makeText(this, "You are already logged in", Toast.LENGTH_SHORT).show();
            finish();
        }



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, RegistrationActivity.class));
            }
        });
    }
}