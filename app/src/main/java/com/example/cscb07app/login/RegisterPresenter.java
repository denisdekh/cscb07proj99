package com.example.cscb07app.login;

public class RegisterPresenter implements LoginContract.RegisterPresenter {
    private LoginContract.RegisterModel model;
    private LoginContract.View view;

    public RegisterPresenter(LoginContract.RegisterModel model, LoginContract.View view) {
        this.model = model;
        this.view = view;
    }

    public void checkAccount() {
        String name = view.getName();
        String email = view.getEmail();
        String username = view.getUsername();
        String password = view.getPassword();
        String usertype = view.getUserType();
        //Ensure none of the fields are empty
        if (name.equals("")) {
            view.displayMessage("Name field is empty");
        } else if (email.equals("")) {
            view.displayMessage("Email field is empty");
        } else if (username.equals("")) {
            view.displayMessage("Username field is empty");
        } else if (password.equals("")) {
            view.displayMessage("Password field is empty");
        } else {
            model.accountCreate(name, email, username, password, usertype, view);
        }
    }
}
