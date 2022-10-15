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

import com.example.dontwastefood.Models.UserModel;
import com.example.dontwastefood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    Button btn_register;
    TextView signIn;
    EditText name,email,password;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.btn_signin);
        name = findViewById(R.id.editText_name_register);
        email = findViewById(R.id.editText_email_register);
        password = findViewById(R.id.editText_password_register);
        btn_register = findViewById(R.id.btn_register);

        progressBar = findViewById(R.id.progressbar_register);
        progressBar.setVisibility(View.GONE);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }
    private void createUser(){
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "You must fill in the name field!", Toast.LENGTH_SHORT).show();
            return;
        }
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
        //create user
        auth.createUserWithEmailAndPassword(userEmail,userPassword)
             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                   if (task.isSuccessful()){
                       UserModel userModel = new UserModel(userName,userEmail,userPassword);
                       String id = task.getResult().getUser().getUid();
                       database.getReference().child("Users").child(id).setValue(userModel);

                       progressBar.setVisibility(View.GONE);
                       Toast.makeText(RegistrationActivity.this, "Registration successful",
                                 Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                        }
                   else{

                       progressBar.setVisibility(View.GONE);
                       Toast.makeText(RegistrationActivity.this, "Error: " + task.getException(),
                                  Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}