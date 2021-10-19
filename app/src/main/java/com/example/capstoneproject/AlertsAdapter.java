package com.example.capstoneproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.models.Alert;

import java.util.ArrayList;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.MyViewHolder> {

    private ArrayList<Alert> alertsList;

    public AlertsAdapter(ArrayList<Alert> alertsList) {
        this.alertsList = alertsList;
    }

    private OnItemClickListener alertsListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        alertsListener = listener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSymbolRC;
        private TextView tvNameRC;
        private TextView tvCurrentPriceRC;
        private TextView tvAlertPriceRC;

        public ImageView ivDeleteAlert;

        public MyViewHolder(final View view) {
            super(view);
            tvSymbolRC = view.findViewById(R.id.tvSymbolRC);
            tvNameRC = view.findViewById(R.id.tvNameRC);
            tvCurrentPriceRC = view.findViewById(R.id.tvCurrentPriceRC);
            tvAlertPriceRC = view.findViewById(R.id.tvAlertPriceRC);
            ivDeleteAlert = view.findViewById(R.id.ivDeleteAlert);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (alertsListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            alertsListener.onItemClick(position);
                        }
                    }
                }
            });

            ivDeleteAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (alertsListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            alertsListener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alerts, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String symbol = alertsList.get(position).getSymbol();
        String name = alertsList.get(position).getName();
        double currentPrice = alertsList.get(position).getCurrentPrice();
        double alertPrice = alertsList.get(position).getAlertPrice();

        holder.tvSymbolRC.setText(symbol);
        holder.tvNameRC.setText(name);
        holder.tvCurrentPriceRC.setText(String.valueOf(currentPrice));
        holder.tvAlertPriceRC.setText(String.valueOf(alertPrice));
    }

    @Override
    public int getItemCount() {
        return alertsList.size();
    }
}
