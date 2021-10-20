package com.example.capstoneproject.fragments.portfolio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;

import java.util.ArrayList;

public class portfoliostockrecycleradapter extends RecyclerView.Adapter<portfoliostockrecycleradapter.MyViewHolder>{
    private ArrayList<portfoliostock> portfoliostocklist;

    public portfoliostockrecycleradapter(ArrayList<portfoliostock> portfoliostocklist){
        this.portfoliostocklist = portfoliostocklist;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nametext;
        private TextView priceText;
        public MyViewHolder(final View view){
            super(view);
            nametext = view.findViewById(R.id.stockinfotextview);
            priceText = view.findViewById(R.id.stockinfotextviewprice);
        }
    }

    @NonNull
    @Override
    public portfoliostockrecycleradapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.portfolio_recycler_resource,parent,false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull portfoliostockrecycleradapter.MyViewHolder holder, int position) {
        String name = portfoliostocklist.get(position).getStockname();
        String price = portfoliostocklist.get(position).getStockPrice();
        holder.nametext.setText(name);
        holder.priceText.setText(price);
    }

    @Override
    public int getItemCount() {
        return portfoliostocklist.size();
    }
}

