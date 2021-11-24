package com.example.cscb07app.login;

public class RegisterPresenter implements LoginContract.RegisterPresenter {
    private LoginContract.RegisterModel model;
    private LoginContract.View view;

    public RegisterPresenter(LoginContract.RegisterModel model, LoginContract.View view) {
        this.model = model;
        this.view = view;
    }

    public void checkAccount() {
        String username = view.getUsername();
        String password = view.getPassword();
        String usertype = view.getUserType();
        if(username.equals("")) {
            view.displayMessage("Username field is empty");
        } else {
            model.accountCreate(username, password, usertype, view);
        }
    }
}
