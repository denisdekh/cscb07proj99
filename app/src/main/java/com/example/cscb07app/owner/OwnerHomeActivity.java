package com.example.cscb07app.owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cscb07app.R;
import com.example.cscb07app.login.LoginModel;
import com.example.cscb07app.login.RegisterActivity;
import com.example.cscb07app.owner.store_manager.StoreManagerActivity;
import com.example.cscb07app.owner.customer_orders.CustomerOrdersActivity;
import com.example.cscb07app.store.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.SnapshotHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**Home Menu for Store Owner, only accessible by Store Owner Accounts.
 * Login screen must sends the Owner username in the Intent to initialize an Owner Class.*/

public class OwnerHomeActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String STORE_ID_EXTRA = "STORE_ID_EXTRA";
    protected Owner account;
    private TextView OwnerInfoTxtView;
    private String username;

    public void getOwnerAccount(DataSnapshot snapshot){
        String username = (String)snapshot.child("username").getValue();
        String password = (String)snapshot.child("password").getValue();
        String storeId = (String)snapshot.child("storeId").getValue();
        this.account = new Owner(username, password);
        this.account.setStoreId(storeId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        // Retrieve account information
        Intent intent = getIntent();
        username = intent.getStringExtra(LoginModel.USERNAME);

        DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference("Account").child("Owner").child(username);
        accountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getOwnerAccount(snapshot);
                displayAccountInformation();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // nothing
            }
        });

        OwnerInfoTxtView = findViewById(R.id.OwnerInfoTextView);
        View CustomerOrdersBut = findViewById(R.id.CustomerOrdersBut);
        View StoreManagerBut = findViewById(R.id.StoreManagerBut);

        CustomerOrdersBut.setOnClickListener(this);
        StoreManagerBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();
        if (buttonId == R.id.StoreManagerBut)
            this.openStoreManager();
        else if (buttonId == R.id.CustomerOrdersBut)
            this.openCustomerOrders();
    }

    private void displayAccountInformation(){
        String infoText = String.format("Username: %s\n" +
                                        "Role: Store Owner", account.getUsername());
        OwnerInfoTxtView.setText(infoText);
    }

    private void createNewStore(DatabaseReference ref){
        /* Create a new store and update Owner's storeId */

        final Store newStore = new Store(account.storeId, "", "");

        DatabaseReference storeCount = ref.child("noOfStores");
        storeCount.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                int storeCount = Integer.parseInt((String)task.getResult().getValue());

                // create a new storeId
                String newStoreId = "s" + (storeCount+1);

                // create a new store on the database
                ref.child("noOfStores").setValue("" + (storeCount+1));

                ref.child("Stores").child(newStoreId).setValue(newStore);
                ref.child("Stores").child(newStoreId).child("items").setValue("");
                ref.child("Stores").child(newStoreId).child("orders").setValue("");

                // update Owner's storeId
                ref.child("Account").child("Owner").child(account.getUsername()).child("storeId").setValue(newStoreId);
                account.setStoreId(newStoreId);

                startStoreManagerActivity(newStoreId);
            }
        });
    }

    private void startStoreManagerActivity(String extraId){
        Intent startStoreManager = new Intent(this, StoreManagerActivity.class);
        startStoreManager.putExtra(STORE_ID_EXTRA, extraId);
        startActivity(startStoreManager);
    }

    private void openStoreManager(){
        if (this.account.storeId.equals("")){
            // create a new store and open Store Manager
            createNewStore(FirebaseDatabase.getInstance().getReference());
        }
        else{
            // open Store Manager using existing storeId
            startStoreManagerActivity(this.account.storeId);
        }
    }

    private void openCustomerOrders(){
        String warningMessage = "Your store has not been set up, click Store Manager to set up your store.";

        if (account.storeId.equals("")){
            Toast.makeText(this, warningMessage, Toast.LENGTH_SHORT).show();
        }
        else {
            Intent startCustomerOrders = new Intent(this, CustomerOrdersActivity.class);
            startCustomerOrders.putExtra(STORE_ID_EXTRA, account.storeId);
            startActivity(startCustomerOrders);
        }
    }
}