package com.example.cscb07app.store;

import com.example.cscb07app.product.Product;

import java.util.List;

public class Store {
    public String description;
    public String name;
    public String id;
    public List<Product> items;
    public List<String> orders;


    public Store() {

    }
    public Store(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;


    }
}
