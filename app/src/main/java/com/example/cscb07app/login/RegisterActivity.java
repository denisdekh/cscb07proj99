package com.example.cscb07app.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cscb07app.R;
import com.example.cscb07app.owner.OwnerHomeActivity;
import com.example.cscb07app.customer.CustomerHomeActivity;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity implements LoginContract.View {

    public static final String USERNAME_MESSAGE = "com.example.USERNAME";
    public static final String PASSWORD_MESSAGE = "com.example.PASSWORD";
    public static final String USERTYPE_MESSAGE = "com.example.USERTYPE";

    private LoginContract.RegisterPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public String getUsername() {
        EditText usernameTextEdit = findViewById(R.id.username);
        return usernameTextEdit.getText().toString();
    }

    public String getPassword() {
        EditText passwordTextEdit = findViewById(R.id.password);
        return passwordTextEdit.getText().toString();
    }

    public String getUserType() {
        Spinner usertypeTextEdit = findViewById(R.id.user);
        return usertypeTextEdit.getSelectedItem().toString();
    }

    public void toSignInButton(View view) {
        Intent intent1 = new Intent(this, LoginActivity.class);
        startActivity(intent1);
    }

    public void RegisterButton(View view) {
        presenter = new RegisterPresenter(new RegisterModel(), this);
        presenter.checkAccount();


        /*
        if(getUserType().equals("Customer")) {
            Intent intent = new Intent(this, CustomerHomeActivity.class);
            intent.putExtra(USERNAME_MESSAGE, getUsername());
            startActivity(intent);
        } else if (getUserType().equals("Owner")) {
            Intent intent = new Intent(this, OwnerHomeActivity.class);
            intent.putExtra(USERNAME_MESSAGE, getUsername());
            startActivity(intent);
        } */
    }
}
