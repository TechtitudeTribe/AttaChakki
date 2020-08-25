package com.example.attachakki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneActivity extends AppCompatActivity {
    private EditText phone;
    private Button continu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        phone=(EditText) findViewById(R.id.number);
        continu=(Button) findViewById(R.id.button);

        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=phone.getText().toString().trim();
                if(number.isEmpty()){
                    phone.setError("Number is required");
                    phone.requestFocus();
                    return;
                }
                String phoneNumber="+91"+number;
                String email=getIntent().getStringExtra("emailId");
                String pwd=getIntent().getStringExtra("password");

                Intent intent= new Intent(PhoneActivity.this,VerifyPhone.class);
                intent.putExtra("emailId",email);
                intent.putExtra("password",pwd);
                intent.putExtra("phonenumber",phoneNumber);
                startActivity(intent);
            }
        });
    }
}
