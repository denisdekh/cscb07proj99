package com.example.cscb07app.owner;


import com.example.cscb07app.User;

public class Owner extends User {
    String storeId;

    public Owner(){

    }

    public Owner(String username, String password, String name, String email, String storeId) {
        super(username, password, name, email);
        setStoreId(storeId);
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreId() {
        return storeId;
    }
}
