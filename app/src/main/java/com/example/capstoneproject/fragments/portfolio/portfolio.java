package com.example.capstoneproject.fragments.portfolio;

import static android.icu.lang.UCharacter.toUpperCase;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstoneproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link portfolio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class portfolio extends Fragment {

    //used for the popup menu
    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;
    private EditText popup_stockname,popup_stockamount;
    Button popup_savebutton,popup_cancelbutton;
    Vector<Integer> stockamounts = new Vector<Integer>();
    Vector<String> stocknames = new Vector<String>();



    myportfoliodatabase myDB;
    ArrayList<String> book_id,book_title,book_author,book_pages;

    FloatingActionButton gotofragment2; //possibly going to be useless

    //for recyclerview
    private ArrayList<portfoliostock> stocksnames;
    portfoliostockrecycleradapter portfoliostockadapter;
    private RecyclerView recyclerview;

    public portfolio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment portfolio.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_portfolio,container,false);
        gotofragment2 = view.findViewById(R.id.addstockcryptobutton);
        myDB = new myportfoliodatabase(getActivity());
        book_id = new ArrayList<>();
        book_author = new ArrayList<>();
        book_title = new ArrayList<>();
        book_pages = new ArrayList<>();

        storeDatainArrays();
        //recycleview
        recyclerview = view.findViewById(R.id.recycleviewstocks);
        portfoliostockadapter = new portfoliostockrecycleradapter(getActivity(),book_id,book_title,book_author,book_pages);
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
    public void createNewDialog(){
        dialogbuilder = new AlertDialog.Builder(getActivity()); //the video used this might be an issue
        final View popupview = getLayoutInflater().inflate(R.layout.popupaddstockcrypto,null);
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
                myportfoliodatabase myDB = new myportfoliodatabase(getActivity());
                myDB.addstock(popup_stockname.getText().toString().trim(), "5", 5);
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
    void storeDatainArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(getActivity(),"No data",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
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