package com.example.dontwastefood.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dontwastefood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView signUp;
    EditText email,password;
    Button btn_login;

    FirebaseAuth auth;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText_email_login);
        password = findViewById(R.id.editText_password_login);
        signUp = findViewById(R.id.btn_signup);
        btn_login = findViewById(R.id.btn_login);

        progressBar = findViewById(R.id.progressbar_login);
        progressBar.setVisibility(View.GONE);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();

                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }
    private void loginUser(){

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();


        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "You must fill in the email field!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "You must fill in the password field!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length() < 8 ){
            Toast.makeText(this, "Password length must be greater than 8 letters!", Toast.LENGTH_SHORT).show();
            return;
        }
        //login user
        auth.signInWithEmailAndPassword(userEmail,userPassword)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful()){

                       progressBar.setVisibility(View.GONE);
                       Toast.makeText(LoginActivity.this, "Login Successfully",
                               Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(LoginActivity.this, MainActivity.class));}
                   else{

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Error: " + task.getException(),
                                Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}