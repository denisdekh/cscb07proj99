package com.example.cscb07app.owner.customer_orders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cscb07app.R;
import com.example.cscb07app.owner.OwnerHomeActivity;

public class ViewCartActivity extends AppCompatActivity {
    TextView textView;
    String cartInfo;
    String storeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        Intent intent = getIntent();
        storeId = intent.getStringExtra(OwnerHomeActivity.STORE_ID_EXTRA);
        textView = findViewById(R.id.cartView);
        getCartInfo();
        textView.setText(cartInfo);
        textView.setMovementMethod(new ScrollingMovementMethod());

    }

    private void getCartInfo(){
        if(getIntent().hasExtra("Cart")){
            cartInfo = getIntent().getStringExtra("Cart");
        }
        else{
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }
    }
}