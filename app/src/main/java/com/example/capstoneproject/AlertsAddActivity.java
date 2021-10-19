package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.capstoneproject.fragments.AlertsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class AlertsAddActivity extends AppCompatActivity {

    TextView tvSymbol;
    public static final String TAG = "AddAlerts"; // testing for logcat

    TextView tvPrice;
    TextView tvName;
    TextView tvPrevious;
    TextView tvChange;
    TextView tvChangePercent;

    EditText etPriceMovement;
    Button btnSetAlert;



    double priceMovementAmount = 0;
    String symbol = "";
    String stockName = "";
    String currentPrice= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_add);

        Intent intent = getIntent();
        String stock = intent.getStringExtra("ticker");

        String TEST_API = "https://api.twelvedata.com/quote?symbol="+stock+"&interval=1day&apikey=c2c894e47847490993e8704e2fe75dd6";

        setContentView(R.layout.activity_alerts_add);

        tvPrice = findViewById(R.id.tvPrice);
        tvSymbol = findViewById(R.id.tvSymbol);
        tvName = findViewById(R.id.tvName);
        tvPrevious = findViewById(R.id.tvPrevious);
        tvChange = findViewById(R.id.tvChange);
        tvChangePercent = findViewById(R.id.tvChangePercent);


        etPriceMovement = findViewById(R.id.etPriceMovement);
        btnSetAlert = findViewById(R.id.btnSetAlert);
        btnSetAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etPriceMovement.getText().toString().isEmpty()) {
                    //
                }else {

                    AlertsFragment.insertItem(symbol, stockName, currentPrice, etPriceMovement.getText().toString());

                    //this is for testing
                    //tvSymbol.setText(etPriceMovement.getText().toString());

                    //priceMovementAmount = Double.parseDouble(etPriceMovement.getText().toString());
                    //goAlertsHomeActivitySuccess();
                    Intent intent = new Intent();
                    //set result code and bundle data for response
                    setResult(RESULT_OK, intent);
                    //closes activity, pass data to parent
                    //AddSuccess();
                    finish();
                }


            }
        });


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(TEST_API, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject results = jsonObject;
                    symbol = results.getString("symbol");
                    stockName = results.getString("name");
                    currentPrice = results.getString("close");
                    String previous = results.getString("previous_close");
                    String change = results.getString("change");
                    String changePercent = results.getString("percent_change");
                    changePercent = changePercent.concat("%");

                    tvSymbol.setText(symbol);
                    tvName.setText(stockName);
                    tvPrice.setText(currentPrice);

                    tvPrevious.setText(previous);
                    tvChange.setText(change);
                    tvChangePercent.setText(changePercent);


                    //change colors based on price change from previous close
                    double changeNum = Double.parseDouble(change);
                    if (changeNum < 0) {
                        tvSymbol.setTextColor(Color.RED);
                        tvPrice.setTextColor(Color.RED);
                        tvPrevious.setTextColor(Color.RED);
                        tvChange.setTextColor(Color.RED);
                        tvChangePercent.setTextColor(Color.RED);
                    }else {
                        tvSymbol.setTextColor(Color.GREEN);
                        tvPrice.setTextColor(Color.GREEN);
                        tvPrevious.setTextColor(Color.GREEN);
                        tvChange.setTextColor(Color.GREEN);
                        tvChange.setText("+"+change);
                        tvChangePercent.setTextColor(Color.GREEN);
                        tvChangePercent.setText("+"+changePercent);
                    }







                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();


                    //error go back to home screen
                    finish();


                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}