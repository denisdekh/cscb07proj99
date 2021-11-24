package com.example.cscb07app.customer;

import com.example.cscb07app.User;
import com.example.cscb07app.order.Order;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Customer extends User {
    ArrayList<Order> orders;

    public Customer(String email, String password) {
        super(email, password);
        orders = new ArrayList<Order>();
    }

}
