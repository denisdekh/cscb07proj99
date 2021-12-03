package com.example.cscb07app.owner.store_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cscb07app.R;
import com.example.cscb07app.owner.OwnerHomeActivity;
import com.example.cscb07app.product.Product;
import com.google.android.gms.common.util.NumberUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText itemName, itemBrand, itemPrice, itemDesc;
    private Button confirmBut, deleteBut;
    private String storeId, itemId;
    private DatabaseReference storeItemRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        storeId = intent.getStringExtra(OwnerHomeActivity.STORE_ID_EXTRA);
        itemId = intent.getStringExtra(StoreManagerActivity.EXTRA_ITEM_ID);
        Log.i("testNewItem", storeId + " " + itemId);

        itemName = (EditText)findViewById(R.id.EditItemNameEditText);
        itemBrand = (EditText)findViewById(R.id.EditItemBrandEditText);
        itemPrice = (EditText)findViewById((R.id.EditItemPriceEditText));
        itemDesc = (EditText)findViewById(R.id.EditItemDescEditText);

        confirmBut = (Button)findViewById(R.id.EditItemConfirmButton);
        deleteBut = (Button)findViewById(R.id.EditItemDelButton);

        storeItemRef = FirebaseDatabase.getInstance().getReference("Stores").child(storeId).child("items");
        storeItemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product tempProduct = new Product();

                // create a new item if itemId does not exist.
                if (!(snapshot.child(itemId).exists())){
                    confirmBut.setText("Create Item");
                    deleteBut.setVisibility(View.GONE);
                } else{
                    confirmBut.setText("Edit Item");
                    deleteBut.setVisibility(View.VISIBLE);

                    tempProduct = snapshot.child(itemId).getValue(Product.class);
                    itemName.setText(tempProduct.getName());
                    itemBrand.setText(tempProduct.getBrand());
                    itemPrice.setText(String.valueOf(tempProduct.getPrice()));
                    itemDesc.setText(tempProduct.getDescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        confirmBut.setOnClickListener(this);
        deleteBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == confirmBut.getId()){
            // change/create item at given Id
            editItem();
        }else{
            // delete item at given Id
            deleteItem();
        }
    }

    private void editItem(){
        String name = itemName.getText().toString();

        if (!(itemPrice.getText().toString().matches("[0-9]+[\\.]?[0-9]*"))){
            Toast.makeText(this, "You must enter a number for item price", Toast.LENGTH_SHORT).show();
        }

        else if (name.equals("")){
            Toast.makeText(this, "You cannot leave the name blank", Toast.LENGTH_SHORT).show();
        }
        else{
            double price = Double.parseDouble(itemPrice.getText().toString());
            String brand = itemBrand.getText().toString();
            String description = itemDesc.getText().toString();

            this.storeItemRef.child(itemId).setValue(new Product(itemId, name, brand, description, price));
            Toast.makeText(this, "Item Successfully updated", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteItem(){
        this.storeItemRef.child(itemId).setValue(null);
        Toast.makeText(this, "Item Successfully deleted.", Toast.LENGTH_SHORT).show();
    }
}