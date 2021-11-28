package com.example.cscb07app.owner;


import com.example.cscb07app.User;

public class Owner extends User {
    String storeId;

    public Owner(){

    }

    public Owner(String email, String password) {
        super(email, password);
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreId() {
        return storeId;
    }
}
