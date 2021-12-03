package com.example.cscb07app.owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cscb07app.R;
import com.example.cscb07app.login.LoginModel;
import com.example.cscb07app.login.RegisterActivity;
import com.example.cscb07app.owner.store_manager.StoreManagerActivity;
import com.example.cscb07app.owner.customer_orders.CustomerOrdersActivity;
import com.example.cscb07app.store.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.SnapshotHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**Home Menu for Store Owner, only accessible by Store Owner Accounts.
 * Login screen must sends the Owner username in the Intent to initialize an Owner Class.*/

public class OwnerHomeActivity extends AppCompatActivity implements View.OnClickListener, OwnerHomeContract.View{

    public static final String STORE_ID_EXTRA = "STORE_ID_EXTRA";
    protected Owner account;
    private TextView OwnerInfoTxtView;

    private OwnerHomeContract.Model model = new OwnerHomeModel();
    private OwnerHomeContract.Presenter presenter = new OwnerHomePresenter(this, model);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        // Retrieve account information
        Intent intent = getIntent();
        String username = intent.getStringExtra(LoginModel.USERNAME);

        model.getOwnerAccount(username, presenter, this);

        OwnerInfoTxtView = findViewById(R.id.OwnerInfoTextView);
        View CustomerOrdersBut = findViewById(R.id.CustomerOrdersBut);
        View StoreManagerBut = findViewById(R.id.StoreManagerBut);

        CustomerOrdersBut.setOnClickListener(this);
        StoreManagerBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();
        if (buttonId == R.id.StoreManagerBut)
            presenter.openStoreManager(this);
        else if (buttonId == R.id.CustomerOrdersBut)
            presenter.openCustomerOrders(this);
    }

    @Override
    public void displayAccountInformation(){
        String infoText = String.format("Username: %s\n" +
                                        "Role: Store Owner", presenter.getAccount().getUsername());
        OwnerInfoTxtView.setText(infoText);
    }
}