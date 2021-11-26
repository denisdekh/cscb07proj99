package com.example.cscb07app.owner.store_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cscb07app.R;
import com.example.cscb07app.owner.OwnerHomeActivity;
import com.example.cscb07app.store.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manager);

        Intent intent = getIntent();
        this.storeId = intent.getStringExtra(OwnerHomeActivity.STORE_ID_EXTRA);

        // Retrieve Store information
        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference("Stores").
                child(this.storeId);
        ValueEventListener storelistener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                store = snapshot.getValue(Store.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // when reading failed, no need.
            }
        };
        storeRef.addValueEventListener(storelistener);

        editButton = (Button)findViewById(R.id.EditStoreNameBut);
        textViewStoreName = (TextView)findViewById(R.id.TextViewStoreName);
        editButton.setOnClickListener(this);

        this.displayStoreName();
    }

    @Override
    public void onResume(){
        super.onResume();
        this.displayStoreName();
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