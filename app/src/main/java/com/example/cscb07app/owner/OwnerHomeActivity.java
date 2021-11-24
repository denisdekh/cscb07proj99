package com.example.cscb07app.owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cscb07app.R;
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

// TODO: add intent to other activities
/**Important notice: Owner class methods and intent extra for username can change in the future.*/

public class OwnerHomeActivity extends AppCompatActivity implements View.OnClickListener{

    protected Owner account;
    private TextView OwnerInfoTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        // Retrieve account information
        String username = getIntent().getStringExtra("username");
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

    // this should be either an interface, or implemented in User class.
    private void displayAccountInformation(){
        String infoText = String.format("Username: %s\n" +
                                        "Role: Store Owner", account.getUsername());
        OwnerInfoTxtView.setText(infoText);
    }

    private void openStoreManager(){

    }

    private void openCustomerOrders(){

    }
}