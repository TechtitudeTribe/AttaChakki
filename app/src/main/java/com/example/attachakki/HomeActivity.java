package com.example.attachakki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
   CircleImageView setting;
   CircleImageView profile;
   CircleImageView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       setting=findViewById(R.id.settings);
       profile=findViewById(R.id.img_contact);
       add=findViewById(R.id.add_contact);

       setting.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent(HomeActivity.this, Setting.class);
               startActivity(i);
           }
       });
       profile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent(HomeActivity.this, Profile.class);
               startActivity(i);
           }
       });

    }
}