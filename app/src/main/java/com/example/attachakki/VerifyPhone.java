package com.example.attachakki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class VerifyPhone extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editText;
    private Button signIn;
    private DatabaseReference stuRef;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progress);
        editText=findViewById(R.id.code);
        signIn=findViewById(R.id.button);
        currentUser = mAuth.getCurrentUser().getUid();
        stuRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);


        String phonenumber=getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=editText.getText().toString().trim();
                if(code.isEmpty()|| code.length()<6){
                    editText.setError("Enter Code...");
                    editText.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
    }

    private void verifyCode(String code) {

        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationId, code);
        signInwithCredential(credential);
    }

    private void signInwithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String first = getIntent().getStringExtra("firstName");
                            String last = getIntent().getStringExtra("lastName");
                            String comp = getIntent().getStringExtra("company");

                            HashMap upload = new HashMap();
                            upload.put("First Name", first);
                            upload.put("Last Name", last);
                            upload.put("Company", comp);

                            stuRef.updateChildren(upload).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        String email = getIntent().getStringExtra("emailId");
                                        String pwd = getIntent().getStringExtra("password");
                                        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(VerifyPhone.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (!task.isSuccessful()) {
                                                    Toast.makeText(VerifyPhone.this, "SignUp Unsuccessful, please Try Again", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Intent intent = new Intent(VerifyPhone.this, HomeActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                }
                                            }

                                        });

                                    } else {
                                        String message = task.getException().getMessage();
                                        Toast.makeText(getApplicationContext(), "Error Occurred : " + message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(VerifyPhone.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void sendVerificationCode(String number) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,mCallBack);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken){
            super.onCodeSent(s,forceResendingToken);
            verificationId=s;
        }
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential){
            String code=phoneAuthCredential.getSmsCode();
            if(code!=null){
                editText.setText(code);
                verifyCode(code);
            }
        }
        @Override
        public void onVerificationFailed(FirebaseException e){
            Toast.makeText(VerifyPhone.this,e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

}
