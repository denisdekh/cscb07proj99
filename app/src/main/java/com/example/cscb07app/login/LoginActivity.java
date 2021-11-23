package com.example.cscb07app.login;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cscb07app.R;
import com.example.cscb07app.customer.Customer;
import com.example.cscb07app.owner.Owner;
import com.example.cscb07app.owner.OwnerHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    public static final String USERNAME_MESSAGE = "com.example.USERNAME";
    public static final String PASSWORD_MESSAGE = "com.example.PASSWORD";
    public static final String USERTYPE_MESSAGE = "com.example.USERTYPE";

    DatabaseReference ref_accounts = FirebaseDatabase.getInstance().getReference("Account");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void toRegisterButton(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void SignInButton(View view) {
        EditText usernameTextEdit = findViewById(R.id.username);
        EditText passwordTextEdit = findViewById(R.id.password);
        
        signIn(usernameTextEdit.getText().toString(),
                passwordTextEdit.getText().toString());
    }

    /* Login procedure depending on whether it is an owner or customer.  */
    private void signIn(String username, String password) {
        ref_accounts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot owners_ref = snapshot.child("Owner");
                DataSnapshot customers_ref = snapshot.child("Customer");
                /* Checks if username exists under Owner in database */
                if (owners_ref.child(username).exists()) {
                    Owner current = owners_ref.child(username).getValue(Owner.class);
                    if (current.getPassword().equals(password)) {
                        Toast.makeText(LoginActivity.this, "You have logged in", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                /* Checks if username exists under Customer in database */
                } else if (customers_ref.child(username).exists()) {
                    Customer current = customers_ref.child(username).getValue(Customer.class);
                    if (current.getPassword().equals(password)) {
                        Toast.makeText(LoginActivity.this, "You have logged in", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                /* Username does not exist under customer or owner in database */
                } else {
                    Toast.makeText(LoginActivity.this, "No such username exists", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //No need
            }
        });
    }
}