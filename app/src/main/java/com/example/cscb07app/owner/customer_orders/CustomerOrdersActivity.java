package com.example.cscb07app.owner.customer_orders;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cscb07app.R;
import com.example.cscb07app.owner.OwnerHomeActivity;

import android.content.Intent;
import android.os.Bundle;

public class CustomerOrdersActivity extends AppCompatActivity {

    private String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);

        Intent intent = getIntent();
        this.storeId = intent.getStringExtra(OwnerHomeActivity.STORE_ID_EXTRA);
    }
}