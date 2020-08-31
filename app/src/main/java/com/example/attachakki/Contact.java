package com.example.attachakki;

import com.google.firebase.database.Exclude;

public class Contact {
    private String name;
    private String phone;
    private String photo;
    private String mKey;

    public Contact(){

    }

    public Contact(String name, String phone, String photo) {
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Exclude
    public String getKey(){
        return mKey;
    }

    @Exclude
    public void setKey(String key){
        mKey= key;
    }
}

