package com.example.cscb07app.owner.store_manager;

import com.example.cscb07app.R;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07app.product.Product;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>{

    private List<Product> productList;
    private final StoreManagerActivity context;

    public ItemRecyclerAdapter(List<Product> productList, StoreManagerActivity context){
        this.productList = productList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView name, price, description, brand;
        private final View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.orderId);
            price = (TextView) itemView.findViewById(R.id.ItemPriceTextViewCard);
            description = (TextView) itemView.findViewById(R.id.productsinfo);
            brand = (TextView) itemView.findViewById(R.id.customer);
            layout = itemView.findViewById(R.id.OrderCardLayout);
        }
    }

    @NonNull
    @Override
    public ItemRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_card, parent, false);
        return new ItemRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Log.i("testPosition", String.valueOf(position));
        Product p = productList.get(position);
        holder.name.setText(p.getName());
        holder.price.setText(("$"+p.getPrice()));
        holder.description.setText(p.getDescription());
        holder.brand.setText(p.getBrand());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.openEditItemInfo(p.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
