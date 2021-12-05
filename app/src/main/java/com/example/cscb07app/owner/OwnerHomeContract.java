package com.example.cscb07app.owner;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

interface OwnerHomeContract {
    interface View {
        void displayAccountInformation();
    }

    interface Model {
        void getOwnerAccount(String username, OwnerHomeContract.Presenter presenter, OwnerHomeContract.View view);
        void createNewStore(OwnerHomeContract.Presenter presenter, Context context);
    }

    abstract class Presenter {
        Owner account;

        abstract Owner getAccount();
        abstract void setAccount(Owner account);
        abstract void openStoreManager(Context context);
        abstract void openCustomerOrders(Context context);
        abstract void logout(AppCompatActivity context);
    }
}
