package com.example.cscb07app.order;

import com.example.cscb07app.product.Product;

import java.util.HashMap;

public class Order {
    //Fields
    HashMap<String, Integer> cart;
    //unordered pairs of the form (product id, amount)

    //Constructors
    public Order(){
        //initializes the HashMap
        cart = new HashMap<>();
    }

    //Methods

    //Method for adding an item of a certain quantity to the list
    public void addItem(Product p, int i){
        //p is the product to be added, i is the number of items to add
        //if the item is not in the hashmap, we add it with a value of i.
        //if the item is in the hashmap, we add i to the current value.

        //Product p must not be null.
        if(p == null) return;
        if(!cart.containsKey(p.getId())){
            //the item is not in the order
            cart.put(p.getId(), i); //adds the item to the cart
        } else {
            //The item is in the order
            //the out method will replace the current item with the new
            //one if the keys are the same
            cart.put(p.getId(), cart.get(p.getId()) + i);
        }
    }

    //method for removing a certain quantity of an item from the list
    public void remItem(Product p, int i){
        //p is the product to be removed, i is the number of items to remove
        //however, the quantity of items cannot go below 0. If it does, the
        //item should be removed from the HashMap.

        //if the item does not exist in the hashmap, it should not be able to
        //be removed, so we do nothing in that case.
        if(p == null) return;
        if(!cart.containsKey(p.getId())){
            //the item is not in the order, so we remove nothing
            return;
        } else{
            //the item is in the order, so we remove the desired quantity
            //if the quantity to be removed would have the new quantity be less than or equal to 0,
            //the item is removed from the HashMap
            if(cart.get(p.getId()) - i <= 0){
                cart.remove(p.getId());
            } else{
                cart.put(p.getId(), cart.get(p.getId()) - i);
            }
        }
    }
}
