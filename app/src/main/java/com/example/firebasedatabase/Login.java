package com.example.firebasedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText email_et;
    EditText pass_et;
    Button login;
    Button login_signup;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        if (user != null) {
            startActivity(new Intent(this, Home.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString();
                String pass = pass_et.getText().toString();

                LoginUser(email, pass);

                email_et.setText("");
                pass_et.setText("");

            }
        });

        login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });

    }

    private void LoginUser(String name, String pass) {

        auth.signInWithEmailAndPassword(name, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Login", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, Home.class));
                } else {
                    Toast.makeText(Login.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void init() {

        email_et = findViewById(R.id.login_email);
        pass_et = findViewById(R.id.login_pass);
        login = findViewById(R.id.login_btn);
        login_signup = findViewById(R.id.login_signup_btn);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

    }
}
