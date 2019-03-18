package com.example.firebasedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    Button btn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    TextView name_tv;TextView email_tv;
    TextView id_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        name_tv=findViewById(R.id.home_name);
        id_tv=findViewById(R.id.home_id);
        email_tv=findViewById(R.id.home_email);

        btn=findViewById(R.id.login_logout_btn);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String id=dataSnapshot.child("id").getValue(String.class);
                String name=dataSnapshot.child("name").getValue(String.class);
                String email=dataSnapshot.child("email").getValue(String.class);

                id_tv.setText(id);
                email_tv.setText(email);
                name_tv.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(Home.this,Login.class));
            }
        });



    }
}
