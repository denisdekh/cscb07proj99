package com.example.cscb07app.login;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginModel implements LoginContract.LoginModel {

    DatabaseReference ref_accounts = FirebaseDatabase.getInstance().getReference("Account");




    @Override
    public void accountExists(String username, String password, LoginContract.View view) {
        ref_accounts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot owners_ref = snapshot.child("Owner");
                DataSnapshot customers_ref = snapshot.child("Customer");
                String data_password;
                //Checks if username exists under Owner in database
                if (owners_ref.child(username).exists()) {
                    data_password = owners_ref.child(username).child("password").getValue().toString();
                    if (data_password.equals(password)) {
                        view.displayMessage("You have logged in");
                    } else {
                        view.displayMessage("Incorrect Password");
                    }
                    //Checks if username exists under Customer in database
                } else if (customers_ref.child(username).exists()) {
                    data_password = customers_ref.child(username).child("password").getValue().toString();
                    if (data_password.equals(password)) {
                        view.displayMessage("You have been logged in");
                    } else {
                        view.displayMessage("Incorrect Password");
                    }
                } else {
                    //Username does not exist under customer or owner in database
                    view.displayMessage("No such username exists");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}