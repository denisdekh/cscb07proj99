package com.example.cscb07app.customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.cscb07app.R;
import com.example.cscb07app.login.LoginModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cscb07app.order.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerMakeOrderActivity extends AppCompatActivity {

Order order;
TextView totalCost;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_customer_make_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.finish_order:
                // User chose the "finish order" item

                order.sendOrder(order.totalCost);
                Toast.makeText(this, "Order successfully submitted", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_make_order);

        Intent intent = getIntent();
        String id = intent.getStringExtra(CustomerHomeActivity.STORE_ID);
        String username = intent.getStringExtra(LoginModel.USERNAME);
        ScrollView scroll = new ScrollView(this);
        this.addContentView(scroll, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(content);
        totalCost  = new TextView(this);
        totalCost.setText("Total: $0.0");
        totalCost.setGravity(Gravity.CENTER);
        totalCost.setTextSize(25);
        totalCost.setPadding(20,20,20,20);

        //ConstraintLayout scrollParent = (ConstraintLayout) findViewById(R.id.scroll_parent);
        content.addView(totalCost);

        order = new Order(username, id);
        getProducts(this, content, id);

    }

    public void addProduct(String id, String name, String desc, String price, String brand, Context context, LinearLayout scroll) {
        if (name.equals("")) return;
        LinearLayout product = new LinearLayout(context);
        LinearLayout name_desc = new LinearLayout(context);
        LinearLayout brand_price = new LinearLayout(context);
        LinearLayout buttons = new LinearLayout(context);
        TextView productName = new TextView(context);
        TextView productDesc = new TextView(context);
        TextView productBrand = new TextView(context);
        TextView productPrice = new TextView(context);

        name_desc.setLayoutParams(new LinearLayout.LayoutParams(400, LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        productName.setLayoutParams(params);
        productName.setText(name);
        productName.setGravity(Gravity.LEFT);
        productName.setPadding(30, 30, 30, 30);
        productName.setTypeface(null, Typeface.BOLD);

        productBrand.setLayoutParams(params);
        productBrand.setText(brand);
        productBrand.setGravity(Gravity.LEFT);
        productBrand.setPadding(30, 30, 30, 30);
        productBrand.setTypeface(null, Typeface.BOLD);

        productDesc.setLayoutParams(params);
        productDesc.setText(desc);
        productDesc.setGravity(Gravity.LEFT);
        productDesc.setPadding(30, 30, 30, 40);

        productPrice.setLayoutParams(params);
        productPrice.setText("$" + price);
        productPrice.setGravity(Gravity.LEFT);
        productPrice.setPadding(30, 30, 30, 40);

        product.setOrientation(LinearLayout.HORIZONTAL);
        name_desc.setOrientation(LinearLayout.VERTICAL);

        brand_price.setOrientation(LinearLayout.VERTICAL);
        brand_price.addView(productBrand);
        brand_price.addView(productPrice);

        product.addView(name_desc);
        product.addView(brand_price);

        Button plus = new Button(context);
        Button minus = new Button(context);
        TextView num = new TextView(context);
        num.setText("0");
        LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(120,120);
        plus.setLayoutParams(button_params);
        minus.setLayoutParams(button_params);

        buttons.setOrientation(LinearLayout.HORIZONTAL);
        minus.setText("-");
        plus.setText("+");
        buttons.addView(minus);
        buttons.addView(num);
        buttons.addView(plus);
        buttons.setGravity(Gravity.CENTER);
        button_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        buttons.setLayoutParams(button_params);
        name_desc.addView(productName);
        name_desc.addView(productDesc);
        product.addView(buttons);
        product.setPadding(40, 40, 40, 40);

        params.setMargins(20,20,20,20);
        product.setLayoutParams(params);
        product.setBackground(ContextCompat.getDrawable(context, R.drawable.layout_bg));
        product.setClipToOutline(true);

        minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int items = Integer.parseInt(num.getText().toString());
                if (items > 0) {
                    items -= 1;
                    num.setText(Integer.toString(items));
                    order.remItem(id);
                    double total = order.getTotalCost();
                    order.setTotalCost(order.getTotalCost() - Double.parseDouble(price));
                    totalCost.setText("Total: $" + Double.toString(order.getTotalCost()));
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int items = Integer.parseInt(num.getText().toString());
                items += 1;
                num.setText(Integer.toString(items));
                order.addItem(id);
                double total = order.getTotalCost();
                order.setTotalCost(order.getTotalCost() + Double.parseDouble(price));
                totalCost.setText("Total: $" + Double.toString(order.getTotalCost()));
            }
        });

        scroll.addView(product);
    }

    public void getProducts(Context context, LinearLayout scroll, String id) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Stores").child(id).child("items");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String id = child.getKey();
                    String name = child.child("name").getValue().toString();
                    String desc = child.child("description").getValue().toString();
                    String price = child.child("price").getValue().toString();
                    String brand = child.child("brand").getValue().toString();

                    addProduct(id, name, desc, price, brand, context, scroll);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}