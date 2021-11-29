package com.example.cscb07app.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cscb07app.login.LoginModel;
import com.example.cscb07app.store.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cscb07app.R;
import com.example.cscb07app.login.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomeActivity extends AppCompatActivity {
    ArrayList<Store> stores = new ArrayList<Store>();

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
                String username = intent.getStringExtra(LoginModel.USERNAME);
                Intent intent2 = new Intent(this, CustomerSettingsActivity.class);
                intent.putExtra("com.example.USERNAME", username);
                startActivity(intent2);
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
        String username = intent.getStringExtra(RegisterActivity.USERNAME_MESSAGE);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.stores_layout);
        linearLayout.setBackgroundColor(0x55665566);

        getStores(this, linearLayout);
    }

    public void addStore(String id, String name, String desc, Context context, LinearLayout linearLayout) {
        Store s = new Store(id, name, desc);
        stores.add(s);
        Button textView = new Button(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                250));
        textView.setText(s.name);
        textView.setBackgroundColor(0xff66ff66);
        //textView.setGravity(Gravity.CENTER);
        textView.setPadding(20, 20, 20, 20);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code to open the store's page
                String message = "opening store: "+ s.name;
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });

        linearLayout.addView(textView);
        Log.d("test", "added store: " + s.name);
    }

    public void getStores(Context context, LinearLayout linearLayout) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Stores");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String id = child.getKey();
                    String name = child.child("name").getValue().toString();
                    String desc = child.child("description").getValue().toString();

                    addStore(id, name, desc, context, linearLayout);
                }
                
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}