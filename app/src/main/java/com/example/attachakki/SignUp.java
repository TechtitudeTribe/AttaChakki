package com.example.attachakki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout emailId,password;
    TextInputEditText ed_email,ed_password;
    Button continu,login;
    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mFirebaseAuth= FirebaseAuth.getInstance();
        emailId=findViewById(R.id.email);
        password= findViewById(R.id.password);
        ed_email=findViewById(R.id.ed_email);
        ed_password= findViewById(R.id.ed_password);
        continu=findViewById(R.id.continu);
        login=findViewById(R.id.login);

        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = ed_email.getText().toString();
                final String pwd = ed_password.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Please enter email Id");
                    ed_email.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter your password");
                    ed_password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(SignUp.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "SignUp Unsuccessful, please Try Again", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(SignUp.this, PhoneActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }

                    });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUp.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}