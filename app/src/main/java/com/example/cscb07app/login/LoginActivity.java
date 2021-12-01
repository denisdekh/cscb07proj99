package com.example.cscb07app.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cscb07app.R;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{

    public static final String USERNAME_MESSAGE = "com.example.USERNAME";
    public static final String PASSWORD_MESSAGE = "com.example.PASSWORD";
    public static final String USERTYPE_MESSAGE = "com.example.USERTYPE";

    private LoginContract.LoginPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    public String getEmail() {
        return "";
    }

    public String getName() {
        return "";
    }

    //Dummy function for mockito testing
    public void valid() {

    }
    //Dummy function for mockito testing
    public void invalid() {

    }

    public String getUserType() {
        return "";
    }

    public void toRegisterButton(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void SignInButton(View view) {
        presenter = new LoginPresenter(new LoginModel(), this);
        presenter.checkAccount();
    }
}