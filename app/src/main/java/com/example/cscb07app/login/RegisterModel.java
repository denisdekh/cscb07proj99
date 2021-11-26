package com.example.cscb07app.login;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cscb07app.customer.Customer;
import com.example.cscb07app.owner.Owner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterModel implements LoginContract.RegisterModel {

    DatabaseReference ref_accounts = FirebaseDatabase.getInstance().getReference("Account");



    @Override
    public void accountCreate(String username, String password, String usertype, LoginContract.View view) {
        ref_accounts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot ref_owners = snapshot.child("Owner");
                DataSnapshot ref_customers = snapshot.child("Customer");
                //Ensure the username does not already exist under customer and owner
                if (!ref_owners.child(username).exists() && !ref_customers.child(username).exists()) {
                    //If the user is registering an owner account
                    if (usertype.equals("Owner")) {
                        ref_accounts.child("Owner").child(username).child("username").setValue(username);
                        ref_accounts.child("Owner").child(username).child("password").setValue(password);
                        ref_accounts.child("Owner").child(username).child("storeId").setValue("");
                        //If the user is registering a customer account
                    } else {
                        ref_accounts.child("Customer").child(username).child("username").setValue(username);
                        ref_accounts.child("Customer").child(username).child("password").setValue(password);
                    }
                    view.valid();
                    view.displayMessage("Your account has been created");
                } else {
                    view.invalid();
                    view.displayMessage("The username already exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
