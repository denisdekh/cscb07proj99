package com.example.cscb07app.owner.customer_orders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<String> orderIds, customers, completed,productsInfo;
    Context context;
    public MyAdapter(Context ct, ArrayList<String> orderIds, ArrayList<String> customers, ArrayList<String> completed, ArrayList<String> productsInfo){
        this.orderIds = orderIds;
        this.customers = customers;
        this.completed = completed;
        this.productsInfo = productsInfo;
        context = ct;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.orderIdtext.setText("Order Id: " +orderIds.get(position));
        holder.customertext.setText("Customer: "+customers.get(position));
        holder.completedtext.setText("Completed: "+completed.get(position));
        if (Boolean.parseBoolean(completed.get(position))){
            holder.completedtext.setTextColor(Color.GREEN);
        }
        else{
            holder.completedtext.setTextColor(Color.RED);
        }
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewCartActivity.class);
                intent.putExtra("Cart",productsInfo.get(position));
                context.startActivity(intent);
            }
        });
        holder.changeCompletedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Orders").child(orderIds.get(position)).child("completed");
                ref.setValue(!Boolean.parseBoolean(completed.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderIds.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView orderIdtext,customertext,completedtext,productsInfotext;
        ConstraintLayout mainLayout;
        Button changeCompletedButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdtext = itemView.findViewById(R.id.orderId1);
            customertext = itemView.findViewById(R.id.customer1);
            completedtext = itemView.findViewById(R.id.completed);
            productsInfotext = itemView.findViewById(R.id.productsInfo1);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            changeCompletedButton = itemView.findViewById(R.id.changeCompletedStatusButton);
        }

    }
}
