package com.example.cscb07app.customer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cscb07app.store.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cscb07app.R;
import com.example.cscb07app.login.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomeActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        Intent intent = getIntent();
        String username = intent.getStringExtra(RegisterActivity.USERNAME_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        /*
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(0xff99ccff);
        */

        getStores();


    }

    public void addText(Store s) {
        TextView textView = new TextView(this);
        textView.setText(s.name);
        textView.setPadding(20, 20, 20, 20);
        Log.d("test", "added textview");
    }

    public void getStores() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Stores");
        ArrayList<Store> stores = new ArrayList<Store>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String id = child.getKey();
                    String name = child.child("store name").getValue().toString();
                    String desc = child.child("description").getValue().toString();
                    Store store = new Store(id, name, desc);
                    stores.add(store);
                    addText(store);
                    Log.d("test", "Stores size: " + Integer.toString(stores.size()));
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}