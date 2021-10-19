package com.example.capstoneproject.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.capstoneproject.AlertsAdapter;
import com.example.capstoneproject.AlertsAddActivity;
import com.example.capstoneproject.R;
import com.example.capstoneproject.models.Alert;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Headers;


public class AlertsFragment extends Fragment {
    String apiSymbols = "aapl,fb";
    public String TEST_API = "https://api.twelvedata.com/price?symbol="+apiSymbols+"&apikey=c2c894e47847490993e8704e2fe75dd6";
    public static final String TAG = "HomeAlerts"; // testing for logcat

    EditText etSearchAlert;
    Button btnSearchAlert;
    static String searchedStock = "";

    SwipeRefreshLayout swipeContainer;

    private static ArrayList<Alert> alertsList;
    private RecyclerView recyclerView;
    static AlertsAdapter adapter;

    public static double currentPriceDB;
    public static double alertPriceDB;


    public AlertsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alerts, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeContainer = view.findViewById(R.id.swipeContainer);

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //getNewCurrentPrice();
                swipeContainer.setRefreshing(false);
            }
        });

        recyclerView = view.findViewById(R.id.rvAlerts);


        setAlertInfo();
        setAdapter();


        etSearchAlert = view.findViewById(R.id.etSearchAlert);
        btnSearchAlert = view.findViewById(R.id.btnSearchAlert);
        btnSearchAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchedStock = etSearchAlert.getText().toString();
                //goAlertsAddActivity();
                Intent intent = new Intent(getActivity(), AlertsAddActivity.class);
                intent.putExtra("ticker", searchedStock);
                startActivity(intent);
            }
        });
    }

    private void setAdapter() {
        adapter = new AlertsAdapter(alertsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AlertsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                testing(position, "Clicked");
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    private void setAlertInfo() {
        alertsList = new ArrayList<>();


    }

    public static void insertItem(String symbol, String name, String currentPrice, String alertPrice) {
        currentPriceDB = Double.parseDouble(currentPrice);
        currentPriceDB = Math.round(currentPriceDB*100.00)/100.00;

        alertPriceDB = Double.parseDouble(alertPrice);
        alertPriceDB = Math.round(alertPriceDB*100.00)/100.00;

        alertsList.add(0, new Alert(symbol,name, currentPriceDB, alertPriceDB));
        adapter.notifyItemInserted(0);
    }

    public static void removeItem(int position) {
        alertsList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void testing(int position, String text) {
        alertsList.get(position).changeText(text);
        adapter.notifyItemChanged(position);
    }



    public static String getSearchedStock() {
        return searchedStock;
    }

    private void goAlertsAddActivity() {
        //navigate to AlertsAddActivity from this activity
        Intent i = new Intent(getContext(), AlertsAddActivity.class);
        startActivity(i);



    }

    public void getNewCurrentPrice() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(TEST_API, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    //for testing
                    //refresh the prices of here
                    JSONObject results = jsonObject.getJSONObject("aapl");
                    String stock1 = results.getString("price");


                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();


                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

    }


}