package com.example.cscb07app.owner.customer_orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07app.R;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.orderIdtext.setText(orderIds.get(position));
        holder.customertext.setText(customers.get(position));
        holder.completedtext.setText(completed.get(position));
        holder.productsInfotext.setText(productsInfo.get(position));
    }

    @Override
    public int getItemCount() {
        return orderIds.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView orderIdtext,customertext,completedtext,productsInfotext;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdtext = itemView.findViewById(R.id.orderId1);
            customertext = itemView.findViewById(R.id.customer1);
            completedtext = itemView.findViewById(R.id.completed);
            productsInfotext = itemView.findViewById(R.id.productsInfo1);
        }
    }
}
