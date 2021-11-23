package com.example.cscb07app.owner;

import com.example.cscb07app.User;

import java.util.ArrayList;

public class Owner extends User {

    /* TODO: Implement a Product Class that holds information about orders
    *   Left as Integer value for now */

    ArrayList<Integer> storeProducts;

    public Owner() {

    }
    public Owner(String username, String password) {
        super(username, password);

    }
}
