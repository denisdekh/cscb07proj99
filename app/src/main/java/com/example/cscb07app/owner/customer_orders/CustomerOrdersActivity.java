package com.example.cscb07app.owner.customer_orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07app.R;
import com.example.cscb07app.owner.OwnerHomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomerOrdersActivity extends AppCompatActivity {
    ArrayList<String> orderIds,customers,productsInfo,completed;
    private String storeId;
    private ArrayList<String> orders;
    String s = "";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        orderIds = new ArrayList<String>();
        customers = new ArrayList<String>();
        productsInfo = new ArrayList<String>();
        completed = new ArrayList<String>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);
        Intent intent = getIntent();
        this.storeId = intent.getStringExtra(OwnerHomeActivity.STORE_ID_EXTRA);
        recyclerView = findViewById(R.id.customerOrdersRecyclerView);
        orderIds.add("hello");
        customers.add("hello");
        productsInfo.add("hello");
        completed.add("true");
        MyAdapter myAdapter = new MyAdapter(this, orderIds,customers,completed,productsInfo);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    /*private void setText(TextView t,String s){
        t.setText(s);
    }

    private void addToArrayList(ArrayList<String> a,String s){
        a.add(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        orders = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);

        Intent intent = getIntent();
        this.storeId = intent.getStringExtra(OwnerHomeActivity.STORE_ID_EXTRA);
        TextView textView = findViewById(R.id.customerOrderTextView);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Stores");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot store: snapshot.getChildren()){
                    if (store.getKey().equals(storeId)){
                        for(DataSnapshot order: store.child("orders").getChildren()){
                            addToArrayList(orders,order.getKey());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference orders_ref = rootRef.child("Orders");

        orders_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot order: snapshot.getChildren()){
                    for (int i = 0;i<orders.size();i++){
                        if (orders.get(i).equals(order.getKey())){
                            String customer = order.child("Customer").getValue(String.class);
                            String store = order.child("Store").getValue(String.class);
                            s+= "Customer: " + customer + " Store: " + store + "\n";
                            for (DataSnapshot product: order.child("cart").getChildren()){
                                String productId = product.getKey();
                                String frequency = product.getValue().toString();
                                s+= "   productId: " + productId+ " frequency: " + frequency + "\n";

                            }
                        }
                    }
                }
                setText(textView,s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

     */
}