package com.example.capstoneproject.fragments.portfolio;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstoneproject.R;

public class updateportfolio extends AppCompatActivity {
    EditText title_input, pages_input;
    Button update_button, delete_button;
    String id, title, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateportfolio);
        title_input = findViewById(R.id.popupaddstockcrypto_stock2);
        pages_input = findViewById(R.id.popupaddstockcrypto_amount2);
        update_button = findViewById(R.id.popupaddstockcrypto_savebutton2);
        delete_button = findViewById(R.id.popupaddstockcrypto_deletebutton);

        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                myportfoliodatabase myDB = new myportfoliodatabase(updateportfolio.this);
                title = title_input.getText().toString().trim();
                pages = pages_input.getText().toString().trim();
                myDB.updateData(id, title, author, pages);
                //from AlertsAdapter - start
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                portfolio fragment = new portfolio();

                //refresh the fragment when you delete a recyclerview/database item
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).addToBackStack(null).commit();

                // -end
                finish();
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myportfoliodatabase myDB = new myportfoliodatabase(updateportfolio.this);
                myDB.deleteOneRow(id);
                //from AlertsAdapter - start
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                portfolio fragment = new portfolio();

                //refresh the fragment when you delete a recyclerview/database item
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).addToBackStack(null).commit();

                // -end
                finish();
            }
        });

    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("author") && getIntent().hasExtra("pages")) {
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

            //Setting Intent Data
            title_input.setText(title);

            pages_input.setText(pages);
            Log.d("stev", title + " " + author + " " + pages);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }


}