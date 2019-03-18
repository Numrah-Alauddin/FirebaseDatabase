package com.example.firebasedatabase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText email_et;
    EditText pass_et;
    EditText name_et;
    Button signup;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString();
                String pass = pass_et.getText().toString();
                String name = name_et.getText().toString();

                signupUser(name,email, pass);


                //First method
             /*   reference.child("id").setValue("123");
                reference.child("name").setValue("abc");
                reference.child("email").setValue("email");*/

                //2nd method
              /*  reference.child("id").setValue("123");
                reference.child("name").setValue("abc");
                reference.child("email").setValue("email");

                startActivity(new Intent(MainActivity.this,Home.class));*/

                email_et.setText("");
                pass_et.setText("");

            }
        });
    }

    private void signupUser(final String name, final String email, String pass) {

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    String id=user.getUid();
                    saveData(id,email,name);
                    Toast.makeText(MainActivity.this, "Signup", Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(MainActivity.this, Home.class));
                } else {
                    Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveData(String id, String email, String name) {


        User user=new User(id,name,email);
        reference.child(id).setValue(user);

    }

    private void init() {

        email_et = findViewById(R.id.email);
        pass_et = findViewById(R.id.pass);
        signup = findViewById(R.id.signup_btn);
        name_et = findViewById(R.id.user_name);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        user=auth.getCurrentUser();

    }
}
