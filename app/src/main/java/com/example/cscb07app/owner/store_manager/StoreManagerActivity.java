package com.example.cscb07app.owner.store_manager;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cscb07app.R;
import com.example.cscb07app.owner.OwnerHomeActivity;

import android.content.Intent;
import android.os.Bundle;

public class StoreManagerActivity extends AppCompatActivity {

    private String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manager);

        Intent intent = getIntent();
        this.storeId = intent.getStringExtra(OwnerHomeActivity.STORE_ID_EXTRA);
    }
}