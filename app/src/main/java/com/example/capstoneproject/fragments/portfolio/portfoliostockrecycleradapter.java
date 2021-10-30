package com.example.capstoneproject.fragments.portfolio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.AlertsAdapter;
import com.example.capstoneproject.R;

import java.util.ArrayList;
import java.util.List;

public class portfoliostockrecycleradapter extends RecyclerView.Adapter<portfoliostockrecycleradapter.MyViewHolder>{

    private final Context context;
    private final ArrayList book_id;
    private final ArrayList book_title;
    private final ArrayList book_author;
    private final ArrayList book_pages;
    private Button button;
    portfoliostockrecycleradapter(Context context,ArrayList book_id, ArrayList book_title, ArrayList book_author, ArrayList book_pages){
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.portfolio_recycler_resource,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_pages_txt.setText(String.valueOf(book_pages.get(position)));
    }

    @Override
    public int getItemCount(){
        return book_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt;
        Button button;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.stockinfotextview);
            book_title_txt = itemView.findViewById(R.id.stockinfotextviewprice);
            book_author_txt = itemView.findViewById(R.id.portfoliorecyclerstockamount);
            book_pages_txt = itemView.findViewById(R.id.textView7);
            button = itemView.findViewById(R.id.deletebutton);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    final myportfoliodatabase myDB = new myportfoliodatabase(view.getContext());
                    String dennis = book_id_txt.getText().toString();
                    myDB.deleteOneRow(dennis);
                }



            });
        }

    }

    /*
    private ArrayList<portfoliostock> portfoliostocklist;

    private Context context;
    private ArrayList book_id,book_title,book_author,book_pages;
    public portfoliostockrecycleradapter(Context context,ArrayList book_id,ArrayList book_title,ArrayList book_author, ArrayList book_pages){
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;
    }

            ArrayList<portfoliostock> portfoliostocklist){
        this.portfoliostocklist = portfoliostocklist;

    }
 /*
    @NonNull
    public MyViewHolder OnCreateViewHolder (@NonNull ViewGroup parent ,int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.portfolio_recycler_resource,parent, false);
        return new MyViewHolder(view);
    }

     */
/*
    public class MyViewHolder extends RecyclerView.ViewHolder{


          private TextView nametext;
        private TextView priceText;
        private TextView amountText;
        public MyViewHolder(final View view){
            super(view);
            nametext = view.findViewById(R.id.stockinfotextview);
            priceText = view.findViewById(R.id.stockinfotextviewprice);
            amountText = view.findViewById(R.id.portfoliorecyclerstockamount);
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
        int amount = portfoliostocklist.get(position).getStockamount();
        holder.nametext.setText(name);
        holder.priceText.setText(price);
        holder.amountText.setText(String.valueOf(amount));

    }

    @Override
    public int getItemCount() {
        return portfoliostocklist.size();
    } */
}

