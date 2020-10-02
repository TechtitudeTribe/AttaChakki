package com.example.attachakki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class EditProfile extends AppCompatActivity {

    private EditText editfirstname, editlastname, editCompany;
    private TextView update;
    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        editfirstname = findViewById(R.id.editfirstname);
        editlastname = findViewById(R.id.editlastname);
        editCompany = findViewById(R.id.editCompany);

        update = findViewById(R.id.update);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);


        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {

                    String firstName = dataSnapshot.child("First Name").getValue().toString();
                    String lastName = dataSnapshot.child("Last Name").getValue().toString();
                    String company = dataSnapshot.child("Gender").getValue().toString();

                    editfirstname.setText(firstName);
                    editlastname.setText(lastName);
                    editCompany.setText("Gender: " + company);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userUpdateInformation();
            }


            private void userUpdateInformation() {

                String first = editfirstname.getText().toString();
                String second = editlastname.getText().toString();
                String third = editCompany.getText().toString();
                HashMap userMap = new HashMap();
                userMap.put("First Name", first);
                userMap.put("Last Name", second);
                userMap.put("Gender", third);

                profileUserRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Profile Info updated successfully...",Toast.LENGTH_SHORT).show();
                        }
                        else
                            {
                            String message = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(),"Error Occurred : "+message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}

