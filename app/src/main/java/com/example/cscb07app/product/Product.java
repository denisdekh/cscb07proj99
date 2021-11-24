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

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand){
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
}
