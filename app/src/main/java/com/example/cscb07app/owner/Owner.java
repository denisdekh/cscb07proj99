package com.example.cscb07app.owner;


import com.example.cscb07app.store.Store;
import com.example.cscb07app.User;

import java.util.ArrayList;

public class Owner extends User {
    String storeId;
    Store store;
    //WILL NOT WORK UNTIL KIERAN MAKES STORE OBJECTS

    /* TODO: Implement a Product Class that holds information about orders
    *   Left as Integer value for now */

    ArrayList<Integer> storeProducts;

    public Owner(String email, String password) {
        super(email, password);
        store = new Store();

    }
    public void setStore(Store s){
        store = s;
    }
    public void setStoreId(String n){
        storeId = n;
    }

}
