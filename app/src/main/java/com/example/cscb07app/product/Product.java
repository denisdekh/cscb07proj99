package com.example.cscb07app.product;

public class Product {
    //Fields
    String id;
    String name;
    String brand;
    String description;
    double price;

    //Constructor
    public Product(String id, String name, String brand, String description, double price){
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
    }

    //Methods
    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
