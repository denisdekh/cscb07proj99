package com.example.cscb07app.owner.store_manager;

import com.example.cscb07app.R;

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

    public ItemRecyclerAdapter(List<Product> productList){
        this.productList = productList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView name, price, description, brand;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.ItemNameTextViewCard);
            price = (TextView) itemView.findViewById(R.id.ItemPriceTextViewCard);
            description = (TextView) itemView.findViewById(R.id.ItemDescriptionTextViewCard);
            brand = (TextView) itemView.findViewById(R.id.ItemBrandTextViewCard);
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
        Product p = productList.get(position);
        holder.name.setText(p.getName());
        holder.price.setText(("$"+p.getPrice()));
        holder.description.setText(p.getDescription());
        holder.brand.setText(p.getBrand());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
