package com.example.attachakki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PhoneActivity extends AppCompatActivity {
    private EditText phone,fname,lname,company;
    private TextView register;
    private DatabaseReference detail;
    private FirebaseAuth mAuth;
    private String currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        phone=(EditText) findViewById(R.id.phone);
        fname=(EditText) findViewById(R.id.firstname);
        lname=(EditText) findViewById(R.id.lastname);
        company=(EditText) findViewById(R.id.company);
        register=(TextView) findViewById(R.id.registerBtn);
        currentUser = mAuth.getCurrentUser().getUid();
        detail= FirebaseDatabase.getInstance().getReference().child("loginDet").child(currentUser);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String number=phone.getText().toString().trim();
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
                    HashMap upload = new HashMap();
                    upload.put("First Name", first);
                    upload.put("Last Name", last);
                    upload.put("Company", comp);

                    detail.updateChildren(upload).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful())
                            {String phoneNumber = "+91" + number;
                                Toast.makeText(getApplicationContext(),"Your account is created successfully...",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PhoneActivity.this, VerifyPhone.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("phonenumber", phoneNumber);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(),"Error Occurred : "+message,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}

