package com.example.cscb07app.login;

public class LoginPresenter implements LoginContract.LoginPresenter{
    private LoginContract.LoginModel model;
    private LoginContract.View view;

    public LoginPresenter(LoginContract.LoginModel model, LoginContract.View view) {
        this.model = model;
        this.view = view;
    }

    public void checkAccount() {
        String username = view.getUsername();
        String password = view.getPassword();
        if(username.equals("")) {
            view.displayMessage("Username field is empty");
        } else {
            model.accountExists(username, password, view);
        }
    }

}
