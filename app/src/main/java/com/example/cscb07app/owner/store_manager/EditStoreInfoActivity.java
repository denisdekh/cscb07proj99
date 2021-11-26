package com.example.cscb07app.owner.store_manager;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cscb07app.R;
import com.example.cscb07app.owner.OwnerHomeActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/** Allows Owner to update their store's name and description*/
/*  Database field may change*/

public class EditStoreInfoActivity extends AppCompatActivity {

    private String storeId;
    private EditText editTextName, editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store_info);

        Intent intent = getIntent();

        editTextName = (EditText)findViewById(R.id.editTextStoreName);
        editTextDescription = (EditText)findViewById(R.id.editTextStoreDescription);
        editTextName.setText(intent.getStringExtra(StoreManagerActivity.EXTRA_STORE_NAME), TextView.BufferType.EDITABLE);
        editTextDescription.setText(intent.getStringExtra(StoreManagerActivity.EXTRA_sTORE_DESCRIPTION), TextView.BufferType.EDITABLE);

        storeId = intent.getStringExtra(OwnerHomeActivity.STORE_ID_EXTRA);
    }

    // Edit information on Firebase if button is clicked
    public void updateStoreInformation(View v){
        String newName = this.editTextName.getText().toString();
        String newDescription = this.editTextDescription.getText().toString();

        // write the new data to database
        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference("Stores").
                child(this.storeId);
        storeRef.child("name").setValue(newName);
        storeRef.child("description").setValue(newDescription);
    }
}