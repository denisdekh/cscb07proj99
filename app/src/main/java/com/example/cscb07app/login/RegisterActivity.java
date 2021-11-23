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

public class RegisterActivity extends AppCompatActivity {

    public static final String USERNAME_MESSAGE = "com.example.USERNAME";
    public static final String PASSWORD_MESSAGE = "com.example.PASSWORD";
    public static final String USERTYPE_MESSAGE = "com.example.USERTYPE";

    DatabaseReference ref_accounts = FirebaseDatabase.getInstance().getReference("Account");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void toSignInButton(View view) {
        Intent intent1 = new Intent(this, LoginActivity.class);
        startActivity(intent1);
    }

    public void RegisterButton(View view) {
        Intent intent = new Intent(this, OwnerHomeActivity.class);
        EditText usernameTextEdit = findViewById(R.id.username);
        EditText passwordTextEdit = findViewById(R.id.password);
        Spinner userTypeEdit = findViewById(R.id.user);
        Register(usernameTextEdit.getText().toString(), passwordTextEdit.getText().toString(), userTypeEdit.getSelectedItem().toString());
    }

    public void Register(String username, String password, String usertype) {
        ref_accounts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot ref_owners = snapshot.child("Owner");
                DataSnapshot ref_customers = snapshot.child("Customer");
                /* Ensure the username does not already exist under customer and owner */
                if (!ref_owners.child(username).exists() && !ref_owners.child(username).exists()) {
                    /* If the user is registering an owner account */
                    if (usertype.equals("Owner")) {
                        Owner current = new Owner(username, password);
                        ref_accounts.child("Owner").child(username).setValue(current);
                    /* If the user is registering a customer account */
                    } else {
                        Customer current = new Customer(username, password);
                        ref_accounts.child("Customer").child(username).setValue(current);
                    }
                    Toast.makeText(RegisterActivity.this, "Your account has been created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
