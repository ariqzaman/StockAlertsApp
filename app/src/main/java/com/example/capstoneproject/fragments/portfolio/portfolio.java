package com.example.capstoneproject.fragments.portfolio;

import static android.icu.lang.UCharacter.toUpperCase;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Timer;
import java.util.TimerTask;
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
        //uncomment for actual release of the app ig
        //updatestock();
        book_id = new ArrayList<>();
        book_author = new ArrayList<>();
        book_title = new ArrayList<>();
        book_pages = new ArrayList<>();
        testbutton = view.findViewById(R.id.testbutton);

        System.out.println("test");
        storeDatainArrays();
        recyclerview = view.findViewById(R.id.recycleviewstocks);
        portfoliostockadapter = new portfoliostockrecycleradapter(getActivity(), book_id, book_title, book_author, book_pages);
        recyclerview.setAdapter(portfoliostockadapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        gotofragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewDialog();

            }
        });
        testbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                System.out.println("AAAAAAAAA");
                tryredraw();
        }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                updatestock();

                tryredraw();
            }

        }, 60000, 61000);
        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tryredraw();
                        }
                    });


                }

        }, 5000, 65000);
        return view;
    }


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
                            tryredraw();

                        } catch (JSONException e) {

                            e.printStackTrace();


                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        System.out.println("Failed");

                    }
                });


                dialog.dismiss();

            }
        });
    }



    void storeDatainArrays() {

        Cursor cursor = myDB.readAllData();

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

    void returnstock(String stocknames,Vector<String> stockss) {
        AsyncHttpClient client = new AsyncHttpClient();
        String testapi = "https://api.twelvedata.com/price?symbol=" + stocknames + "&apikey=f0b21df90101477184b43faf1d393bc9";
        System.out.println(testapi);
        ArrayList<String> testing = new ArrayList<>();
        client.get(testapi, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject results = jsonObject;
                    for(int i=0;i<stockss.size();i++){
                        JSONObject p = results.getJSONObject(stockss.get(i));
                        String symbol = p.getString("price");
                        testing.add(p.getString("price"));
                        System.out.println(testing.size());
                        System.out.println(symbol);
                    }


                } catch (JSONException e) {

                    e.printStackTrace();


                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });


    }

    void tryredraw(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerview.setAdapter(null);
                recyclerview.setLayoutManager(null);
                book_id = new ArrayList<>();
                book_author = new ArrayList<>();
                book_title = new ArrayList<>();
                book_pages = new ArrayList<>();
                storeDatainArrays();
                portfoliostockadapter = new portfoliostockrecycleradapter(getActivity(), book_id, book_title, book_author, book_pages);
                recyclerview.setAdapter(portfoliostockadapter);
                recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });

    }

    void updatestock(){
        Cursor cursor = myDB.readAllData();
        String stocknamesb = "";
        ArrayList<String> stonks = new ArrayList<>();
        ArrayList<String> testvector = new ArrayList();
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

                stocknamesb = stocknamesb + cursor.getString(1).toString() + ",";
                stonks.add(cursor.getString(1));
            }
        }
        AsyncHttpClient client = new AsyncHttpClient();
        String testapi = "https://api.twelvedata.com/price?symbol=" + stocknamesb + "&apikey=f0b21df90101477184b43faf1d393bc9";
        System.out.println(testapi);
        client.get(testapi, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONObject results = jsonObject;
                    for(int i=0;i<stonks.size();i++)
                    {

                        JSONObject p = results.getJSONObject(stonks.get(i));

                       testvector.add(p.getString("price"));


                    }
                    System.out.println(testvector.size());
                    myportfoliodatabase testdbthing = new myportfoliodatabase(getActivity());
                    Cursor cursor3 = myDB.readAllData();
                    int testnum = 0;
                    if (cursor3.getCount() == 0) {
                        Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                    } else {
                        while (cursor3.moveToNext()) {
                            testdbthing.updateData(cursor3.getString(0),
                                    cursor3.getString(1),
                                    testvector.get(testnum),
                                    cursor3.getString(3));
                            System.out.println(testvector.get(testnum));
                            testnum = testnum + 1;
                        }

                    }
                } catch (JSONException e) {
                    System.out.println("JSONEXCEPTION1");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                System.out.println("JSONEXCEPTION2");
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