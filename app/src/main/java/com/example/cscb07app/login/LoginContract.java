package com.example.cscb07app.login;

public interface LoginContract {

    interface LoginModel {
        void accountExists(String username, String password, LoginContract.View view);

    }

    interface LoginPresenter {
        void checkAccount();
    }

    interface RegisterModel {
        void accountCreate(String name, String email, String username, String password, String usertype, LoginContract.View view);

    }

    interface RegisterPresenter {
        void checkAccount();
    }

    interface View {
        String getName();
        String getEmail();
        String getUsername();
        String getPassword();
        String getUserType();
        String getConfirmPassword();
        void displayMessage(String message);
        void displayMessage(String message, String type);
    }
}
