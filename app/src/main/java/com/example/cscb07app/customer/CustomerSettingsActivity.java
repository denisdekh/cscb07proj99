package com.example.cscb07app.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cscb07app.R;
import com.example.cscb07app.login.LoginModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerSettingsActivity extends AppCompatActivity {

    DatabaseReference ref_user = FirebaseDatabase.getInstance().getReference("Account").child("Customer");

    private boolean passwordShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_settings);
        getInformation();
    }

    public void getInformation() {
        String customerusername = getIntent().getStringExtra("USERNAME");
        ref_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(customerusername).child("name").getValue().toString();
                String email = snapshot.child(customerusername).child("email").getValue().toString();
                String username = snapshot.child(customerusername).child("username").getValue().toString();
                setInformation(name, email, username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setInformation(String name, String email, String username) {
        TextView nameText = findViewById(R.id.displayCustomerName);
        nameText.setText(name);
        TextView emailText = findViewById(R.id.displayCustomerEmail);
        emailText.setText(email);
        TextView usernameText = findViewById(R.id.displayCustomerUsername);
        usernameText.setText(username);
    }

    public void onPasswordClick(View view) {
        String customerusername = getIntent().getStringExtra("USERNAME");
        Button passwordTextField = findViewById(R.id.displayCustomerPassword);
        if (!passwordShow) {
            passwordShow = true;
            ref_user.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String password = snapshot.child(customerusername).child("password").getValue().toString();
                    passwordTextField.setText(password);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            passwordTextField.setText(R.string.censoredPassword);
            passwordShow = false;
        }
    }
}