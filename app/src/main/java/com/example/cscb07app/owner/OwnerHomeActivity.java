package com.example.cscb07app.owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cscb07app.R;
import com.example.cscb07app.login.LoginActivity;
import com.example.cscb07app.owner.store_manager.StoreManagerActivity;
import com.example.cscb07app.owner.customer_orders.CustomerOrdersActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**Home Menu for Store Owner, only accessible by Store Owner Accounts.
 * Login screen must sends the Owner username in the Intent to initialize an Owner Class.*/

/**Important notice: Owner class methods and intent extra for username can change in the future.*/

public class OwnerHomeActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String STORE_ID_EXTRA = "STORE_ID_EXTRA";
    protected Owner account;
    private TextView OwnerInfoTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        // Retrieve account information
        String username = getIntent().getStringExtra(LoginModel.USERNAME);
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
            this.openStoreManager();
        else if (buttonId == R.id.CustomerOrdersBut)
            this.openCustomerOrders();
    }

    /* This method is currently not necessary, and should be implemented as an interface because
       CustomerHomeActivity may also use it.*/
    private void displayAccountInformation(){
        String infoText = String.format("Username: %s\n" +
                                        "Role: Store Owner", account.getUsername());
        OwnerInfoTxtView.setText(infoText);
    }

    private void openStoreManager(){
        Intent startStoreManager = new Intent(this, StoreManagerActivity.class);
        startStoreManager.putExtra(STORE_ID_EXTRA, this.account.storeId);
        startActivity(startStoreManager);
    }

    private void openCustomerOrders(){
        Intent startCustomerOrders = new Intent(this, CustomerOrdersActivity.class);
        startCustomerOrders.putExtra(STORE_ID_EXTRA, this.account.storeId);
    }
}