package com.example.attachakki;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends Fragment {
    private TextView profirstname, prolastname, procompanyname;
    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    String currentUser;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater , @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_info, container,false);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);

        profirstname = v.findViewById(R.id.profirstname);
        prolastname = v.findViewById(R.id.prolastname);
        procompanyname = v.findViewById(R.id.procompanyname);

        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {

                    String firstName = dataSnapshot.child("First Name").getValue().toString();
                    String lastName = dataSnapshot.child("Last Name").getValue().toString();
                    String companyName=dataSnapshot.child("Company Name").getValue().toString();

                    profirstname.setText(firstName);
                    prolastname.setText(lastName);
                    procompanyname.setText(companyName);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }


}
