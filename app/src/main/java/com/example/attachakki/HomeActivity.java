package com.example.attachakki;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
   CircleImageView setting;
   CircleImageView profile;
    private final int PICK_CONTACT=1;
    CircleImageView add_contact;
    private DatabaseReference mDatabaseRef;

    private RecyclerView mRecyclerView;
    private ContactAdapter mAdapter;
    private List<Contact> mContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       setting=findViewById(R.id.settings);
       profile=findViewById(R.id.img_contact);
        add_contact= findViewById(R.id.add_contact);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mContacts= new ArrayList<>();

        mDatabaseRef= FirebaseDatabase.getInstance().getReference("contacts");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Contact contact=postSnapshot.getValue(Contact.class);
                    mContacts.add(contact);
                }
                mAdapter =new ContactAdapter(HomeActivity.this, mContacts);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenContact();
            }
        });
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
    private void OpenContact() {

        Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_CONTACT){
            if(requestCode==RESULT_OK){
                Uri contactData= data.getData();
                Cursor c=getContentResolver().query(contactData,null,null,null,null);

                if(c.moveToFirst()){
                    String name= c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    String phone=c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String photo=c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI));

                    Contact contact= new Contact(name, phone, photo);
                    String contactId= mDatabaseRef.push().getKey();
                    mDatabaseRef.child(contactId).setValue(contact);

                }
            }
        }
    }
}