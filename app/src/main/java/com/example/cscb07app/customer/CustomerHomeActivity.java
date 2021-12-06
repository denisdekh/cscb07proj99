package com.example.cscb07app.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cscb07app.customer.viewOrders.CustomerViewOrders;
import com.example.cscb07app.login.LoginModel;
import com.example.cscb07app.store.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.cscb07app.R;
import com.example.cscb07app.login.RegisterActivity;

import java.util.ArrayList;

public class CustomerHomeActivity extends AppCompatActivity {
    ArrayList<Store> stores = new ArrayList<Store>();
    final public static String STORE_ID = "com.example.app.STOREID";
    String username;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item
                Intent intent = getIntent();
                username = intent.getStringExtra(LoginModel.USERNAME);
                Intent intent2 = new Intent(this, CustomerSettingsActivity.class);
                intent2.putExtra(LoginModel.USERNAME, username);
                startActivity(intent2);
                return true;

            case R.id.customer_logout:
                finish();
                return true;

            case R.id.orders:
                Intent intent1 = getIntent();
                username = intent1.getStringExtra(LoginModel.USERNAME);
                Intent intent3 = new Intent(this, CustomerViewOrders.class);
                intent3.putExtra(LoginModel.USERNAME, username);
                startActivity(intent3);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        Intent intent = getIntent();
        username = intent.getStringExtra(LoginModel.USERNAME);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ScrollView scroll = new ScrollView(this);
        this.addContentView(scroll, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        scroll.addView(linearLayout);

        getStores(this, linearLayout);
    }

    public void addStore(String id, String name, String desc, Context context, LinearLayout linearLayout) {
        if (name.equals("")) return;

        Store s = new Store(id, name, desc);
        stores.add(s);

        LinearLayout store = new LinearLayout(context);
        TextView storeName = new TextView(context);
        TextView storeDesc = new TextView(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        storeName.setLayoutParams(params);
        storeName.setText(s.getName());
        storeName.setGravity(Gravity.LEFT);
        storeName.setPadding(30, 30, 30, 30);
        storeName.setTypeface(null, Typeface.BOLD);

        storeDesc.setLayoutParams(params);
        storeDesc.setText(s.getDescription());
        storeDesc.setGravity(Gravity.LEFT);
        storeDesc.setPadding(30, 30, 30, 40);

        store.setOrientation(LinearLayout.VERTICAL);
        store.addView(storeName);
        store.addView(storeDesc);
        store.setPadding(40, 40, 40, 40);

        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(20,20,20,20);
        store.setLayoutParams(params);
        store.setBackground(ContextCompat.getDrawable(context, R.drawable.layout_bg));
        store.setClipToOutline(true);
        store.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code to open the store's page
                Intent intent = new Intent(context, CustomerMakeOrderActivity.class);
                intent.putExtra(STORE_ID, id);
                intent.putExtra(LoginModel.USERNAME, username);
                startActivity(intent);
            }
        });

        linearLayout.addView(store);
    }

    public void getStores(Context context, LinearLayout linearLayout) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Stores");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String id = child.getKey();
                    String name = child.child("name").getValue().toString();
                    String desc = child.child("description").getValue().toString();
                    Store s = new Store(id, name, desc);
                    Log.d("test", Integer.toString(stores.size()));
                    if(!stores.contains(s)) addStore(id, name, desc, context, linearLayout);

                }
                
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}