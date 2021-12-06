package com.example.cscb07app.owner;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cscb07app.owner.customer_orders.CustomerOrdersActivity;
import com.example.cscb07app.owner.store_manager.StoreManagerActivity;
import com.google.firebase.database.FirebaseDatabase;

public class OwnerHomePresenter extends OwnerHomeContract.Presenter {

    final String STORE_ID_EXTRA = "STORE_ID_EXTRA";
    OwnerHomeContract.View view;
    OwnerHomeContract.Model model;

    public OwnerHomePresenter(OwnerHomeContract.View view, OwnerHomeContract.Model model){
        this.view = view;
        this.model = model;
    }

    @Override
    public Owner getAccount(){
        return account;
    }

    @Override
    public void setAccount(Owner account) {
        this.account = account;
    }

    protected void startStoreManagerActivity(String extraId, Context context){
        Intent startStoreManager = new Intent(context, StoreManagerActivity.class);
        startStoreManager.putExtra(STORE_ID_EXTRA, extraId);
        context.startActivity(startStoreManager);
    }

    @Override
    public void openStoreManager(Context context) {
        if (this.account.storeId.equals("")){
            // create a new store and open Store Manager
            model.createNewStore(this, context);
        }
        else{
            // open Store Manager using existing storeId
            startStoreManagerActivity(account.getStoreId(), context);
        }
    }

    @Override
    public void openCustomerOrders(Context context) {
        String warningMessage = "Your store has not been set up, click Store Manager to set up your store.";

        if (account.getStoreId().equals("")){
            Toast.makeText(context, warningMessage, Toast.LENGTH_SHORT).show();
        }
        else {
            Intent startCustomerOrders = new Intent((Context)view, CustomerOrdersActivity.class);
            startCustomerOrders.putExtra(STORE_ID_EXTRA, account.getStoreId());
            context.startActivity(startCustomerOrders);
        }
    }

    @Override
    public void logout(AppCompatActivity context){
        context.finish();
    }
}
