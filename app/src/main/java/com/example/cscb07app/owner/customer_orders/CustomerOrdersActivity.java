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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomerOrdersActivity extends AppCompatActivity {
    private String storeId;
    String s = "";
    RecyclerView recyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);
        Intent intent = getIntent();
        context = this;
        this.storeId = intent.getStringExtra(OwnerHomeActivity.STORE_ID_EXTRA);
        recyclerView = findViewById(R.id.customerOrdersRecyclerView);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        makeAdapter(ref);

        /*makeOrderIds();
        makeCustomersCompletedlistProducts();
        makeProductInfo();
        */
    }

    void addStringtoArrayList(ArrayList<String> a ,String s){
        a.add(s);
    }

    void makeAdapter(DatabaseReference s){
        s.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> orderIds = new ArrayList<String>();
                ArrayList<String> customers = new ArrayList<String>();
                ArrayList<String> completed = new ArrayList<String>();
                ArrayList<String> productInfo = new ArrayList<String>();
                if (storeId!=null) {
                    for (DataSnapshot order : snapshot.child("Stores").child(storeId).child("orders").getChildren()) {
                        orderIds.add(order.getKey());
                    }
                    for (String orderId : orderIds) {
                        String customer = snapshot.child("Orders").child(orderId).child("username").getValue().toString();
                        customers.add(customer);
                        String completedString = snapshot.child("Orders").child(orderId).child("completed").getValue().toString();
                        completed.add(completedString);
                        String product_info_string = "CART:\n\n";
                        for (DataSnapshot product : snapshot.child("Orders").child(orderId).child("cart").getChildren()) {
                            String productId = product.getKey();
                            String productName = "does not exist";
                            String productBrand = "does not exist";
                            String productPrice = " does not exist";
                            String productDescription = "does not exist";
                            product_info_string += " Product Id: " + productId + " Frequency: " + product.getValue() + " \n";
                            if (snapshot.child("Stores").child(storeId).child("items").child(productId).child("name").getValue() != null) {
                                productName = snapshot.child("Stores").child(storeId).child("items").child(productId).child("name").getValue().toString();
                            }
                            if (snapshot.child("Stores").child(storeId).child("items").child(productId).child("brand").getValue() != null) {
                                productBrand = snapshot.child("Stores").child(storeId).child("items").child(productId).child("brand").getValue().toString();
                            }
                            if (snapshot.child("Stores").child(storeId).child("items").child(productId).child("price").getValue() != null) {
                                productPrice = snapshot.child("Stores").child(storeId).child("items").child(productId).child("price").getValue().toString();
                            }
                            if (snapshot.child("Stores").child(storeId).child("items").child(productId).child("description").getValue() != null) {
                                productDescription = snapshot.child("Stores").child(storeId).child("items").child(productId).child("description").getValue().toString();
                            }
                            product_info_string += "    Name: " + productName + "\n";
                            product_info_string += "    Brand: " + productBrand + "\n";
                            product_info_string += "    Price : " + productPrice + "\n";
                            product_info_string += "    Description: " + productDescription + "\n";
                        }
                        productInfo.add(product_info_string);
                        MyAdapter myAdapter = new MyAdapter(context, orderIds, customers, completed, productInfo);
                        recyclerView.setAdapter(myAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*void makeOrderIds(DatabaseReference s){
        s.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> ids = new ArrayList<String>();
                for (DataSnapshot order: snapshot.child("orders").getChildren()){
                    addStringtoArrayList(ids,order.getKey());
                }
                orderIds = ids;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void makeCompleted(DatabaseReference o){
        o.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(String orderId: orderIds){
                    String completedstring = snapshot.child(orderId).child("completed").getValue().toString();
                    completed.add(completedstring);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void makeCustomers(DatabaseReference o){
        o.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(String orderId: orderIds){
                    String customerString = snapshot.child(orderId).child("username").getValue().toString();
                    addStringtoArrayList(customers,customerString);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void makeProductsInfo(DatabaseReference o){
        o.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (String orderId: orderIds){
                    String s = "";
                    for(DataSnapshot product: snapshot.child(orderId).child("cart").getChildren()){
                        String productId = product.getKey();
                        String productFrequency = product.getValue().toString();
                        s += "Product Id: " + productId + "\n   product Frequency: " + productFrequency + "\n";

                    }
                    addStringtoArrayList(productsInfo,s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

     */

    /*



    private void addToArrayList(ArrayList<String> a,String s){
        a.add(s);
    }

    private void addArrayListtoArrayList(ArrayList<ArrayList<String>> a, ArrayList<String> s){
        a.add(s);
    }

    void makeOrderIds() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Stores");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot store : snapshot.getChildren()) {
                    if (store.getKey().equals(storeId)) {
                        for (DataSnapshot order : store.child("orders").getChildren()) {
                            addToArrayList(orderIds, order.child("orderId").getValue(String.class));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void makeCustomersCompletedlistProducts(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference orders_ref = rootRef.child("Orders");

        orders_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot order: snapshot.getChildren()){
                    for (int i = 0;i<orderIds.size();i++){
                        if (orderIds.get(i).equals(order.getKey())){
                            String customer = order.child("username").getValue(String.class);
                            String completed1 = order.child("completed").getValue(String.class);

                            addToArrayList(completed,"Completed: " + completed1);
                            addToArrayList(customers,"Customer: "+customer);
                            ArrayList<String> productIds = new ArrayList<String>();
                            ArrayList<String> frequencies = new ArrayList<String>();
                            for (DataSnapshot product: order.child("cart").getChildren()){

                                String productId = product.getKey();
                                String frequency = product.getValue(String.class);
                                addToArrayList(productIds,productId);
                                addToArrayList(frequencies,frequency);

                            }
                            addArrayListtoArrayList(listproductIds,productIds);
                            addArrayListtoArrayList(listproductFrequencys,frequencies);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void makeProductInfo(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Stores");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot store : snapshot.getChildren()) {
                    if (store.getKey().equals(storeId)) {
                        for (int i = 0;i<orderIds.size();i++){
                            String s = "";
                            ArrayList<String> products_list = listproductIds.get(i);
                            ArrayList<String> product_frequencies = listproductFrequencys.get(i);
                            for(DataSnapshot product: store.child("items").getChildren()){
                                String id = product.getKey();
                                for (int j = 0;j<products_list.size();i++){
                                    if (id.equals(products_list.get(j)) ){
                                        String frequency = product_frequencies.get(j);
                                        s+= "Product Id: " + id + "Frequency: " + frequency + "\n";
                                    }
                                }
                                String product_name = product.child("name").getValue(String.class);
                                String product_brand = product.child("brand").getValue(String.class);
                                String product_price = product.child("price").getValue(String.class);
                                String product_description = product.child("description").getValue(String.class);
                                s+= "   Name: " + product_name + "\n";
                                s+= "   Brand: " + product_brand + "\n";
                                s+= "   Price: " + product_price + "\n";
                                s+= "   Description: " + product_description + "\n";

                            }

                        }
                        addToArrayList(productsInfo,s);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
*/





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