package com.example.cscb07app.order;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cscb07app.customer.Customer;
import com.example.cscb07app.product.Product;
import com.example.cscb07app.store.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    //Fields
    public HashMap<String, Integer> cart;
    //unordered pairs of the form (product id, amount)
    public String orderId; //Id of the order
    public String username; //Username of the customer who placed the order
    public String storeId; //Store the order was sent to
    public Boolean completed; //As the name suggests, true or false whether the order is ready for pickup.

    public double totalCost;

    //Constructors

    public Order(String username, String storeId){
        //initializes the HashMap
        cart = new HashMap<>();
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

    public double getTotalCost(){
        return totalCost;
    }
    public void setTotalCost(double totalCost){
        this.totalCost = totalCost;
    }

    //TODO read from the database and return an integer representing the cost of the cart based on
    //TODO the store ID and the prices of the items within the store


    public void calcTotal(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        //we are going to take a one time look at the database and grab the necessary information
        //each item in the cart should be coming from the same store, and so we can look at that one store for prices.
        //for each item in the cart we will get the corresponding price, multiply it by the quantity of items in the cart...
        //and add that to the total variable.

        ref.child("Stores").child(storeId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().getValue() == null){
                    //the store does not exist.
                    Log.i("demo", "Store does not exist");
                } else {
                    //for each product id in the cart
                    double total = 0.00;
                    for(String key: cart.keySet()){
                        //cost of the item in the cart multiplied by the frequency of the item in the cart

                        total += Double.parseDouble(task.getResult().child("items").child(key).child("price").getValue().toString()) * cart.get(key);
                    }
                    setTotalCost(Math.round(total * 100)/100.0);
                }
            }
        });
    }

    //Generates a reference to the firebase database and sends the order to the database under
    //the the orders section, where the orderId is the key to the order
    public void sendOrder(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        //Set the id of the order when sending it, and update the number of orders in the database
        ref.child("noOfOrders").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.getResult().getValue() == null){
                    //This means that the current number of orders in the database is 0/the field
                    //does not yet exist,

                    //In this case we manually set the order count to be 1 and set the order id to be 1
                    int orderCount = 0;
                    String newOrderId = "o" + (orderCount + 1);

                    //Increases the number of stores in the database
                    ref.child("noOfOrders").setValue("" + (orderCount + 1));

                    //Sends the order to the database
                    setOrderId(newOrderId);

                    //Manually setting the entry on firebase
                    ref.child("Orders").child(newOrderId).child("username").setValue(getUsername());
                    ref.child("Orders").child(newOrderId).child("orderId").setValue(getOrderId());
                    ref.child("Orders").child(newOrderId).child("storeId").setValue(getStoreId());
                    ref.child("Orders").child(newOrderId).child("completed").setValue(getCompleted());

                    //adding the order to the stores list of orders
                    ref.child("Stores").child(storeId).child("orders").child(newOrderId).setValue("a");

                    //iterate through each item in the cart adding it to firebase
                    for(String key: cart.keySet()){
                        ref.child("Orders").child(newOrderId).child("cart").child(key).setValue(cart.get(key));
                    }
                } else {
                    int orderCount = Integer.parseInt((String)task.getResult().getValue());

                    //Creates the new order id
                    String newOrderId = "o" + (orderCount + 1);

                    //Increases the number of stores in the database
                    ref.child("noOfOrders").setValue("" + (orderCount + 1));

                    //Sends the order to the database
                    setOrderId(newOrderId); //sets the orderId to be one higher than previous id

                    //Manually setting the entry on firebase
                    ref.child("Orders").child(newOrderId).child("username").setValue(getUsername());
                    ref.child("Orders").child(newOrderId).child("orderId").setValue(getOrderId());
                    ref.child("Orders").child(newOrderId).child("storeId").setValue(getStoreId());
                    ref.child("Orders").child(newOrderId).child("completed").setValue(getCompleted());

                    //adding the order to the stores list of orders
                    ref.child("Stores").child(storeId).child("orders").child(newOrderId).setValue("a");

                    //iterate through each item in the cart adding it to firebase
                    for(String key: cart.keySet()){
                        ref.child("Orders").child(newOrderId).child("cart").child(key).setValue(cart.get(key));
                    }
                }
            }
        });

        //After coming out of the one time listener, our order Id is set according to the values
        //in the database, and our order should have been sent as well

    }
}
