package com.example.capstoneproject.fragments.portfolio;

import static android.icu.lang.UCharacter.toUpperCase;

import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.capstoneproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

import okhttp3.Headers;


public class portfolio extends Fragment {

    //used for the popup menu
    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;
    private EditText popup_stockname, popup_stockamount;
    Button popup_savebutton, popup_cancelbutton;
    Vector<Integer> stockamounts = new Vector<Integer>();
    Vector<String> stocknames = new Vector<String>();


    myportfoliodatabase myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages;

    FloatingActionButton gotofragment2; //possibly going to be useless

    Button testbutton;
    //for recyclerview
    private ArrayList<portfoliostock> stocksnames;
    portfoliostockrecycleradapter portfoliostockadapter;
    private RecyclerView recyclerview;

    public portfolio() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);
        gotofragment2 = view.findViewById(R.id.addstockcryptobutton);
        myDB = new myportfoliodatabase(getActivity());
        book_id = new ArrayList<>();
        book_author = new ArrayList<>();
        book_title = new ArrayList<>();
        book_pages = new ArrayList<>();
        testbutton = view.findViewById(R.id.testbutton);
        storeDatainArrays();
        //recycleview
        recyclerview = view.findViewById(R.id.recycleviewstocks);
        portfoliostockadapter = new portfoliostockrecycleradapter(getActivity(), book_id, book_title, book_author, book_pages);
        recyclerview.setAdapter(portfoliostockadapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerview = view.findViewById(R.id.recycleviewstocks);
        //stocksnames = new ArrayList<>();


        //setAdapter();
        gotofragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigation.findNavController(view).navigate(R.id.action_nav_portfolio_to_addstockcrypto2);
                createNewDialog();

            }
        });
        testbutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                testthing2();
            }
        });

        return view;
    }
/*
    private void setAdapter(){
        portfoliostockrecycleradapter adapter = new portfoliostockrecycleradapter(stocksnames);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapter);
    }
*/

    //this is for the add stock popup
    public void createNewDialog() {
        dialogbuilder = new AlertDialog.Builder(getActivity()); //the video used this might be an issue
        final View popupview = getLayoutInflater().inflate(R.layout.popupaddstockcrypto, null);
        popup_stockname = (EditText) popupview.findViewById(R.id.popupaddstockcrypto_stock);
        popup_stockamount = (EditText) popupview.findViewById(R.id.popupaddstockcrypto_amount);
        popup_cancelbutton = (Button) popupview.findViewById(R.id.popupaddstockcrypto_cancelbutton);
        popup_savebutton = (Button) popupview.findViewById(R.id.popupaddstockcrypto_savebutton);
        dialogbuilder.setView(popupview);
        dialog = dialogbuilder.create();
        dialog.show();


        popup_cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        popup_savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncHttpClient client = new AsyncHttpClient();
                String testapi = "https://cloud.iexapis.com/stable/stock/market/batch?symbols=" + popup_stockname.getText().toString().trim() +"&types=quote&range=1m&last=5&token=sk_312389e990ff49af9d13a20cc770ec95";
                client.get(testapi, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            JSONObject results = jsonObject;
                            JSONObject p = results.getJSONObject(toUpperCase(popup_stockname.getText().toString().trim()));
                            p = p.getJSONObject("quote");
                            myDB.addstock(popup_stockname.getText().toString().trim(), p.getString("latestPrice"), Integer.parseInt(popup_stockamount.getText().toString()));


                        } catch (JSONException e) {

                            e.printStackTrace();


                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        System.out.println("Failed");

                    }
                });

                /*stocknames.add(toUpperCase(popup_stockname.getText().toString()));
                stockamounts.add(Integer.valueOf(popup_stockamount.getText().toString()));
                stocksnames.add(new portfoliostock(toUpperCase(popup_stockname.getText().toString()),"5",2));
                //setAdapter(); */


                dialog.dismiss();

            }
        });
    }

    //test functions
    /*
    private void setstockinfo(){
        stocksnames.add(new portfoliostock("AAPL","5",2));
        stocksnames.add(new portfoliostock("F","6",2));
        stocksnames.add(new portfoliostock("CLF","7",2));
    }
    */
    void storeDatainArrays() {
        Cursor cursor = myDB.readAllData();
        String stocktickers = "";
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                book_id.add(cursor.getString(0));

                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
    }

    void returnstock(String stockname) {
        AsyncHttpClient client = new AsyncHttpClient();
        String testapi = "https://api.twelvedata.com/price?symbol=AAPL,AMZN&apikey=f0b21df90101477184b43faf1d393bc9";
        client.get(testapi, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject results = jsonObject;
                    JSONObject p = results.getJSONObject("AMZN");
                    String symbol = p.getString("price");
                    System.out.println(symbol);
                    System.out.println("test");
                } catch (JSONException e) {

                    e.printStackTrace();


                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });
    }
    /*
    void testthing(){
            Cursor cursor = myDB.readAllData();

            String stocktickers = "";
            if (cursor.getCount() == 0) {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    stocktickers = stocktickers + cursor.getString(1) + ",";

                }
            }
            JSONObject jsonObject;
        System.out.println(stocktickers);
        AsyncHttpClient client = new AsyncHttpClient();
        String testapi = "https://api.twelvedata.com/price?symbol=AAPL,AMZN&apikey=f0b21df90101477184b43faf1d393bc9";
        client.get(testapi, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                jsonObject = json.jsonObject;
                try {
                    JSONObject results = jsonObject;
                    JSONObject p = results.getJSONObject("AMZN");
                    String symbol = p.getString("price");
                    System.out.println(symbol);
                    System.out.println("test");
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });
    }
*/
    void testthing2(){
        AsyncHttpClient client = new AsyncHttpClient();
        String testapi = "https://cloud.iexapis.com/stable/stock/market/batch?symbols=aapl,fb&types=quote&range=1m&last=5&token=sk_312389e990ff49af9d13a20cc770ec95";
        client.get(testapi, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject results = jsonObject;
                    JSONObject p =results.getJSONObject("AAPL");
                    p = p.getJSONObject("quote");
                    String symbol = p.getString("latestPrice");
                    System.out.println(symbol);


                    System.out.println("test");
                } catch (JSONException e) {

                    e.printStackTrace();


                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });
    }

}


/*
Handler handler = new Handler();
Runnable runnable;
int delay = 15*1000; //Delay for 15 seconds.  One second = 1000 milliseconds.


@Override
protected void onResume() {
   //start handler as activity become visible

    handler.postDelayed( runnable = new Runnable() {
        public void run() {
            //do something
        setstockinfo();
            handler.postDelayed(runnable, delay);
        }
    }, delay);

    super.onResume();
}

// If onPause() is not included the threads will double up when you
// reload the activity

@Override
protected void onPause() {
    handler.removeCallbacks(runnable); //stop handler when activity not visible
    super.onPause();
}
*/