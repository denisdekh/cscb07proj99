package com.example.cscb07app.customer;

import com.example.cscb07app.User;
import com.example.cscb07app.order.Order;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Customer extends User {
    ArrayList<String> orderIds;

    public Customer(String email, String password) {
        super(email, password);
        orderIds = new ArrayList<String>();
    }

}
