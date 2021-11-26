package com.example.cscb07app.owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cscb07app.R;
import com.example.cscb07app.login.LoginModel;
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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**Home Menu for Store Owner, only accessible by Store Owner Accounts.
 * Login screen must sends the Owner username in the Intent to initialize an Owner Class.*/

public class OwnerHomeActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String STORE_ID_EXTRA = "STORE_ID_EXTRA";
    protected Owner account;
    private TextView OwnerInfoTxtView;
    protected int storeCount;
    protected String newStoreId;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        // Retrieve account information
        username = getIntent().getStringExtra(LoginModel.USERNAME);
        DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference("Account").
                child("Owner").child(username);
        ValueEventListener accountlistener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                account = snapshot.getValue(Owner.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // when reading failed, no need.
            }
        };
        accountRef.addValueEventListener(accountlistener);
        account.setUsername(username);

        OwnerInfoTxtView = findViewById(R.id.OwnerInfoTextView);
        View CustomerOrdersBut = findViewById(R.id.CustomerOrdersBut);
        View StoreManagerBut = findViewById(R.id.StoreManagerBut);

        this.displayAccountInformation();
        CustomerOrdersBut.setOnClickListener(this);
        StoreManagerBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();
        if (buttonId == R.id.StoreManagerBut)
            this.openStoreManager(this.account.storeId);
        else if (buttonId == R.id.CustomerOrdersBut)
            this.openCustomerOrders(this.account.storeId);
    }

    /* This method is currently not necessary, and should be implemented as an interface because
       CustomerHomeActivity may also use it.*/
    private void displayAccountInformation(){
        String infoText = String.format("Username: %s\n" +
                                        "Role: Store Owner", account.getUsername());
        OwnerInfoTxtView.setText(infoText);
    }

    private void openStoreManager(String id){
        String storeId = "";
        // create a new id for a new store
        if (id.equals("")){
            Store newStore = new Store();

            // One time reader to rea   d number of stores
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            DatabaseReference storeCountRef = ref.child("noOfStores");

            storeCountRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    storeCount = Integer.parseInt(task.getResult().getValue().toString());
                }
            });

            // create a new storeId
            newStoreId = "s" + storeCount+1;

            // create a new store on the database
            ref.child("Stores").child(newStoreId).setValue(newStore);
            storeCountRef.setValue(""+storeCount+1);

            // update Owner's storeId
            ref.child("Account").child("Owner").child(username).child("storeId").setValue(newStoreId);

            storeId = newStoreId;
        }
        else{
            storeId = id;
        }
        Intent startStoreManager = new Intent(this, StoreManagerActivity.class);
        startStoreManager.putExtra(STORE_ID_EXTRA, storeId);
        startActivity(startStoreManager);
    }

    private void openCustomerOrders(String storeId){
        String warningMessage = "Your store has not been set up, click Store Manager to set up your store.";

        if (storeId.equals("")){
            Toast.makeText(this, warningMessage, Toast.LENGTH_SHORT).show();
        }
        else {
            Intent startCustomerOrders = new Intent(this, CustomerOrdersActivity.class);
            startCustomerOrders.putExtra(STORE_ID_EXTRA, storeId);
            startActivity(startCustomerOrders);
        }
    }
}