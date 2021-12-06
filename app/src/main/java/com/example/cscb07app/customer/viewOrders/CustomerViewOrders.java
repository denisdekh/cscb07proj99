package com.example.cscb07app.customer.viewOrders;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07app.R;
import com.example.cscb07app.customer.CustomerHomeActivity;
import com.example.cscb07app.login.LoginModel;
import com.example.cscb07app.order.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerViewOrders extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> orderIds;
    ArrayList<String> storeNames;
    ArrayList<String> totalPrices;
    ArrayList<String> orderStatuses;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_orders);

        Intent i = getIntent();
        String username = i.getStringExtra(LoginModel.USERNAME);

        orderIds = new ArrayList<>();
        storeNames = new ArrayList<>();
        totalPrices = new ArrayList<>();
        orderStatuses = new ArrayList<>();

        populateLists(username);
    }

    public void populateLists(String username){
        //Creating a reference to the database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.child("Account").child("Customer").child(username).child("orders").getChildren()){
                    String orderId = child.getKey().toString();
                    String storeId = snapshot.child("Orders").child(orderId).child("storeId").getValue().toString();

                    //set the parameters for a new order that we will add to the list of the orders at the specified position
                    String storeName = snapshot.child("Stores").child(storeId).child("name").getValue().toString();
                    String totalPrice = snapshot.child("Orders").child(orderId).child("price").getValue().toString();
                    String orderStatus;
                    if((Boolean)snapshot.child("Orders").child(orderId).child("completed").getValue()){
                        orderStatus = "Ready for Pickup!";
                    } else{
                        orderStatus = "Currently Being Prepared!";
                    }

                    insertLists(orderId, storeName, totalPrice, orderStatus);

                }
                setRecyclerAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("warning", "loadPost:onCancelled", error.toException());
            }
        };

        ref.addValueEventListener(listener);
    }

    public void insertLists(String id, String store, String price, String status){
        //The purpose of this method is to insert the items into the list reverse chronologically
        //this mean that an order with id o11 would be inserted after order o32

        //id.substring 1 gives the id without the o identifier.
        int orderNumber = Integer.parseInt(id.substring(1));

        if(orderIds.size() == 0){
            orderIds.add(id);
            storeNames.add(store);
            totalPrices.add("$"+price);
            orderStatuses.add(status);
        }
        else if(orderIds.size() == 1){
            if(Integer.parseInt(orderIds.get(0).substring(1)) > orderNumber){
                orderIds.add(id);
                storeNames.add(store);
                totalPrices.add("$"+price);
                orderStatuses.add(status);
            } else {
                orderIds.add(0, id);
                storeNames.add(0, store);
                totalPrices.add(0, "$"+price);
                orderStatuses.add(0, status);
            }
        }
        else { //size of the list is minimum 2, and so the following insertion algorithm should work
            if(Integer.parseInt(orderIds.get(0).substring(1)) < orderNumber){ //if the id number is higher than every other one, it is the newest in the system -> attach to start
                orderIds.add(0, id);
                storeNames.add(0, store);
                totalPrices.add(0, "$"+price);
                orderStatuses.add(0, status);
            } else if(Integer.parseInt(orderIds.get(orderIds.size()-1).substring(1)) > orderNumber){ //the id at the end of the array is smaller -> oldest -> attach to end
                orderIds.add(id);
                storeNames.add(store);
                totalPrices.add("$"+price);
                orderStatuses.add(status);
            } else {
                for (int i = 0; i < orderIds.size() - 1; i++) { //comparing up to size - 1 gives all the characters while avoiding null pointer
                    if (Integer.parseInt(orderIds.get(i).substring(1)) > orderNumber && Integer.parseInt(orderIds.get(i+1).substring(1)) < orderNumber) {
                        orderIds.add(i+1, id);
                        storeNames.add(i+1, store);
                        totalPrices.add(i+1, "$" + price);
                        orderStatuses.add(i+1, status);
                        break;
                    }
                }
            }
        }
    }

    public void setRecyclerAdapter(){
        recyclerView = findViewById(R.id.recyclerView);
        ViewOrdersAdapter adapter = new ViewOrdersAdapter(orderIds, storeNames, totalPrices, orderStatuses, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
