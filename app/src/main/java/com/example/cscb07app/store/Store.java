package com.example.cscb07app.store;

import com.example.cscb07app.product.Product;

import java.util.List;

// Testing Store class, DO NOT COMMIT

public class Store {
    // description, name, and id should be private or protected
    public String description;
    public String name;
    public String id;
    private List<Product> items;
    private List<String> orders;


    public Store() {

    }
    public Store(String id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
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
}
