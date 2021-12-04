package com.example.cscb07app.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPresenter implements LoginContract.RegisterPresenter {
    private LoginContract.RegisterModel model;
    private LoginContract.View view;

    Pattern namePattern = Pattern.compile("([a-z]|[A-Z]|\\s)+");
    Pattern emailPattern = Pattern.compile("(\\w)+@(\\w)+.(\\w)+");
    Pattern usernamePattern = Pattern.compile("(\\w)+");
    Pattern passwordPattern = Pattern.compile(".{3,}");
    Matcher matcher;

    public RegisterPresenter(LoginContract.RegisterModel model, LoginContract.View view) {
        this.model = model;
        this.view = view;
    }

    public void checkAccount() {
        String name = view.getName();
        String email = view.getEmail();
        String username = view.getUsername();
        String password = view.getPassword();
        String confirmpassword = view.getConfirmPassword();
        String usertype = view.getUserType();
        //Ensure none of the fields are empty
        if (!nameMatchRegex(name)) {
            view.displayMessage("Not a valid name", "name");
        } else if (!emailMatchRegex(email)) {
            view.displayMessage("Not a valid email", "email");
        } else if (!usernameMatchRegex(username)) {
            view.displayMessage("Not a valid username", "username");
        } else if (!passwordMatchRegex(password)) {
            view.displayMessage("Not a valid password", "password");
        } else if (!password.equals(confirmpassword)) {
            view.displayMessage("Passwords do not match", "password");
        } else {
            model.accountCreate(name, email, username, password, usertype, view);
        }
    }

    public boolean nameMatchRegex(String s) {
        matcher = namePattern.matcher(s);
        return matcher.matches();
    }

    public boolean emailMatchRegex(String s) {
        matcher = emailPattern.matcher(s);
        return matcher.matches();
    }

    public boolean usernameMatchRegex(String s) {
        matcher = usernamePattern.matcher(s);
        return matcher.matches();
    }

    public boolean passwordMatchRegex(String s) {
        matcher = passwordPattern.matcher(s);
        return matcher.matches();
    }

}
