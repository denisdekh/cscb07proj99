package com.example.cscb07app.order;

import com.example.cscb07app.customer.Customer;
import com.example.cscb07app.product.Product;
import com.example.cscb07app.store.Store;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Order {
    //Fields
    HashMap<String, Integer> cart;
    //unordered pairs of the form (product id, amount)
    String orderId; //Id of the order
    String username; //Username of the customer who placed the order
    String storeId; //Store the order was sent to
    Boolean completed; //As the name suggests, true or false whether the order is ready for pickup.

    //Constructors
    public Order(String orderId, String username, String storeId){
        //initializes the HashMap
        cart = new HashMap<>();
        this.orderId = orderId;
        this.username = username;
        this.storeId = storeId;
        this.completed = false;
    }

    //Methods

    //Method for adding an item of a certain quantity to the list
    public void addItem(String pId){
        //p is the product to be added, i is the number of items to add
        //if the item is not in the hashmap, we add it with a value of i.
        //if the item is in the hashmap, we add i to the current value.

        if(!cart.containsKey(pId)){
            //the item is not in the order
            cart.put(pId, 1); //adds the item to the cart
        } else {
            //The item is in the order
            //the out method will replace the current item with the new
            //one if the keys are the same
                cart.put(pId, cart.get(pId) + 1);
        }
    }

    //method for removing a certain quantity of an item from the list
    public void remItem(String pId) {
        //p is the product to be removed,
        //however, the quantity of items cannot go below 0. If it does, the
        //item should be removed from the HashMap.

        //if the item does not exist in the hashmap, it should not be able to
        //be removed, so we do nothing in that case.
        if (!cart.containsKey(pId)) {
            //the item is not in the order, so we remove nothing
            return;
        } else {
            //the item is in the order, so we remove the desired quantity
            //if the quantity to be removed would have the new quantity be less than or equal to 0,
            //the item is removed from the HashMap
            if (cart.get(pId) - 1 <= 0) {
                cart.remove(pId);
            } else {
                cart.put(pId, cart.get(pId) - 1);
            }
        }
    }

    public HashMap<String, Integer> getCart() {
        return cart;
    }
    public void setCart(HashMap<String, Integer> cart) {
        this.cart = cart;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getStoreId() {
        return storeId;
    }
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Boolean getCompleted() {
        return completed;
    }
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    //Generates a reference to the firebase database and sends the order to the database under
    //the the orders section, where the orderId is the key to the order
    public void sendOrder(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Orders").child(orderId).setValue(this);
    }
}
