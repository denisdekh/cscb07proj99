package com.example.cscb07app.login;

public interface LoginContract {

    public interface LoginModel {
        public void accountExists(String username, String password, LoginContract.View view);

    }

    public interface LoginPresenter {
        public void checkAccount();
    }

    public interface RegisterModel {
        public void accountCreate(String username, String password, String usertype, LoginContract.View view);

        //boolean registered();
    }

    public interface RegisterPresenter {
        public void checkAccount();

        //boolean registered();
    }

    public interface View {
        public String getUsername();
        public String getPassword();
        public String getUserType();
        public void displayMessage(String message);
    }
}
