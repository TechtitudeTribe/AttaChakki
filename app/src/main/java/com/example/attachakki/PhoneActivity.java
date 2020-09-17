package com.example.attachakki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PhoneActivity extends AppCompatActivity {
    private EditText phone,fname,lname,company;
    private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        phone=(EditText) findViewById(R.id.phone);
        fname=(EditText) findViewById(R.id.firstname);
        lname=(EditText) findViewById(R.id.lastname);
        company=(EditText) findViewById(R.id.company);
        register=(TextView) findViewById(R.id.registerBtn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=phone.getText().toString().trim();
                String first = fname.getText().toString();
                String last = lname.getText().toString();
                String comp = company.getText().toString();


                if(first.isEmpty()){
                    phone.setError("Enter your First name");
                    phone.requestFocus();
                    return;
                }else if(last.isEmpty()) {
                    phone.setError("Enter your Last name");
                    phone.requestFocus();
                    return;
                }else if(comp.isEmpty()){
                    phone.setError("Enter your company name");
                    phone.requestFocus();
                    return;
                }
                else if(number.isEmpty()){
                    phone.setError("Number is required");
                    phone.requestFocus();
                    return;
                }else {
                    String phoneNumber = "+91" + number;
                    String firstname = first;
                    String lastname = last;
                    String company = comp;
                    String email = getIntent().getStringExtra("emailId");
                    String pwd = getIntent().getStringExtra("password");

                    Intent intent = new Intent(PhoneActivity.this, VerifyPhone.class);
                    intent.putExtra("emailId", email);
                    intent.putExtra("password", pwd);
                    intent.putExtra("phonenumber", phoneNumber);
                    intent.putExtra("firstName", firstname);
                    intent.putExtra("lastName", lastname);
                    intent.putExtra("company", company);
                    startActivity(intent);
                }
            }
        });
    }
}

