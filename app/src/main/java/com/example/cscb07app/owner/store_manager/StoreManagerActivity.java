package com.example.cscb07app.owner.store_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07app.R;
import com.example.cscb07app.owner.OwnerHomeActivity;
import com.example.cscb07app.product.Product;
import com.example.cscb07app.store.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/** Activity allows Owner to edit Store information and item listing.*/

/* Require a Store class to hold store information. Note that Store methods and fields may change.*/

/* TODO: Add dynamic, clickable Views that display item information.
         Implement FAB to add new item.
         Read and Write item information from and to the database.
         update onResume to show changed store name (might not needed)*/

public class StoreManagerActivity extends AppCompatActivity implements View.OnClickListener{

    protected static final String EXTRA_STORE_NAME = "EXTRA_STORE_NAME";
    protected static final String EXTRA_sTORE_DESCRIPTION = "EXTRA_sTORE_DESCRIPTION";

    private String storeId;
    protected Store store;
    private Button editButton;
    private TextView textViewStoreName;
    private RecyclerView itemRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manager);

        Intent intent = getIntent();
        this.storeId = intent.getStringExtra(OwnerHomeActivity.STORE_ID_EXTRA);

        // Retrieve Store information
        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference("Stores").
                child(this.storeId);
        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = (String)snapshot.child("name").getValue();
                String description = (String)snapshot.child("description").getValue();
                store = new Store(storeId, name, description);

                // TODO: set up a list of item ids.
                List<Product> tempProductList = new ArrayList<Product>();
                for (DataSnapshot p: snapshot.child("items").getChildren()){
                    Product tempProduct = getProduct(p);
                    if (tempProduct != null)
                        tempProductList.add(tempProduct);
                }
                store.setItems(tempProductList);
                displayStoreName();
                setItemRecyclerAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // nothing
            }
        });

        editButton = (Button)findViewById(R.id.EditStoreNameBut);
        textViewStoreName = (TextView)findViewById(R.id.TextViewStoreName);
        itemRecyclerView = (RecyclerView)findViewById(R.id.ItemListRecyclerView);
        editButton.setOnClickListener(this);
    }

    protected Product getProduct(DataSnapshot snap){
        Product p = snap.getValue(Product.class);
        if (p != null)
            p.setId(snap.getKey());
        return p;
    }

    protected void setItemRecyclerAdapter() {
        ItemRecyclerAdapter adapter = new ItemRecyclerAdapter(store.getItems());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        this.itemRecyclerView.setLayoutManager(layoutManager);
        this.itemRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.itemRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == editButton.getId()){
            openEditStoreInfo();
        }
    }

    private void openEditStoreInfo(){
        String extraName = "";
        String extraDescription = "";

        if (!(store.getName().equals("")) || !(store.getDescription().equals(""))){
            extraName = store.getName();
            extraDescription = store.getDescription();
        }

        Intent openActivity = new Intent(this, EditStoreInfoActivity.class);
        openActivity.putExtra(EXTRA_STORE_NAME, extraName);
        openActivity.putExtra(EXTRA_sTORE_DESCRIPTION, extraDescription);
        openActivity.putExtra(OwnerHomeActivity.STORE_ID_EXTRA, this.storeId);
        startActivity(openActivity);
    }

    private void displayStoreName(){
        String storeName = "Unnamed Store";
        if (!(this.store.getName().equals(""))){
            storeName = this.store.getName();
        }
        textViewStoreName.setText(storeName);
    }
}