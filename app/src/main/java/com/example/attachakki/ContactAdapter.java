package com.example.attachakki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private Context mContext;
    private List<Contact> mContacts;

    public ContactAdapter(Context context, List<Contact> contacts){
        mContext=context;
        mContacts=contacts;
    }
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_contact,parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contactCurrent= mContacts.get(position);
        holder.name.setText(contactCurrent.getName());
        holder.phone.setText(contactCurrent.getPhone());

        if(contactCurrent.getPhoto()!= null){
            Picasso.get().load(contactCurrent.getPhoto()).into(holder.photo);
        }else{
            holder.photo.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView photo;
        public TextView name;
        public TextView phone;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            photo= itemView.findViewById(R.id.img_contact);
            name= itemView.findViewById(R.id.name_contact);
            phone= itemView.findViewById(R.id.phone_contact);
        }
    }
}
