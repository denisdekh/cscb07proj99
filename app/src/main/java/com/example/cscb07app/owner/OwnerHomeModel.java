package com.example.cscb07app.owner;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.cscb07app.owner.store_manager.StoreManagerActivity;
import com.example.cscb07app.store.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OwnerHomeModel implements OwnerHomeContract.Model{

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    public void getOwnerAccount(String username, OwnerHomeContract.Presenter presenter, OwnerHomeContract.View view) {
        DatabaseReference accountRef = ref.child("Account").child("Owner").child(username);
        accountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = (String)snapshot.child("username").getValue();
                String password = (String)snapshot.child("password").getValue();
                String storeId = (String)snapshot.child("storeId").getValue();
                String name = (String)snapshot.child("name").getValue();
                String email = (String)snapshot.child("email").getValue();
                Owner tempOwner = new Owner(username, password, name, email, storeId);
                presenter.setAccount(tempOwner);
                view.displayAccountInformation();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // nothing
            }
        });
    }

    @Override
    public void createNewStore(OwnerHomeContract.Presenter presenter, Context context) {
        final Store newStore = new Store("", "", "");

        DatabaseReference storeCount = ref.child("noOfStores");
        storeCount.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                int storeCount = Integer.parseInt((String)task.getResult().getValue());

                // create a new storeId
                String newStoreId = "s" + (storeCount+1);

                // create a new store on the database
                ref.child("noOfStores").setValue("" + (storeCount+1));

                newStore.setId(newStoreId);
                ref.child("Stores").child(newStoreId).setValue(newStore);
                ref.child("Stores").child(newStoreId).child("items").setValue("");
                ref.child("Stores").child(newStoreId).child("orders").setValue("");
                ref.child("Stores").child(newStoreId).child("lastItemId").setValue("0");

                // update Owner's storeId
                ref.child("Account").child("Owner").child(presenter.getAccount().getUsername()).child("storeId").setValue(newStoreId);
                Owner tempOwner = presenter.getAccount();
                tempOwner.setStoreId(newStoreId);
                presenter.setAccount(tempOwner);

                // open StoreManagerActivity
                Intent startStoreManager = new Intent(context, StoreManagerActivity.class);
                startStoreManager.putExtra("STORE_ID_EXTRA", newStoreId);
                context.startActivity(startStoreManager);
            }
        });
    }
}