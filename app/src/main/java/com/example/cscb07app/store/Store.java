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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }
}
