package com.example.cscb07app.customer.viewOrders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07app.R;

import java.util.ArrayList;

public class ViewOrdersAdapter extends RecyclerView.Adapter<ViewOrdersAdapter.MyViewHolder>{

    ArrayList<String> orderIds;
    ArrayList<String> storeNames;
    ArrayList<String> totalPrices;
    ArrayList<String> orderStatuses;
    Context context;

    public ViewOrdersAdapter(ArrayList<String> orderIds, ArrayList<String> storeNames, ArrayList<String> totalPrices, ArrayList<String> orderStatuses, Context context) {
        this.orderIds = orderIds;
        this.storeNames = storeNames;
        this.totalPrices = totalPrices;
        this.orderStatuses = orderStatuses;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView storeView;
        TextView idView;
        TextView statusView;
        TextView priceView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idView = itemView.findViewById(R.id.OrderIdTextView);
            storeView = itemView.findViewById(R.id.StoreNameTextView);
            statusView = itemView.findViewById(R.id.StatusTextView);
            priceView = itemView.findViewById(R.id.PriceTextView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater inflater = LayoutInflater.from(context);
        //View view = inflater.inflate(R.layout.my_row, parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_orders_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Log.i("demo", ""+orderIds.get(position));


        holder.idView.setText(orderIds.get(position).toString());
        holder.storeView.setText(storeNames.get(position).toString());
        holder.statusView.setText(orderStatuses.get(position).toString());
        holder.priceView.setText(totalPrices.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return orderIds.size();
    }
}
