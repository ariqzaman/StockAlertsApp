package com.example.capstoneproject.fragments.portfolio;

import static android.icu.lang.UCharacter.toUpperCase;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.capstoneproject.R;

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

    Button gotofragment2; //possibly going to be useless

    //for recyclerview
    private ArrayList<portfoliostock> stocksnames;
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


        //recycleview
        recyclerview = view.findViewById(R.id.recycleviewstocks);
        stocksnames = new ArrayList<>();

        setstockinfo();
        setAdapter();
        gotofragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigation.findNavController(view).navigate(R.id.action_nav_portfolio_to_addstockcrypto2);
                createNewDialog();
            }
        });



        return view;
    }

    private void setAdapter(){
        portfoliostockrecycleradapter adapter = new portfoliostockrecycleradapter(stocksnames);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapter);
    }


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
                stocknames.add(toUpperCase(popup_stockname.getText().toString()));
                stockamounts.add(Integer.valueOf(popup_stockamount.getText().toString()));
                stocksnames.add(new portfoliostock(toUpperCase(popup_stockname.getText().toString()),"5"));
                setAdapter();
                dialog.dismiss();

            }
        });
    }
    //test functions
    private void setstockinfo(){
        stocksnames.add(new portfoliostock("AAPL","5"));
        stocksnames.add(new portfoliostock("F","6"));
        stocksnames.add(new portfoliostock("CLF","7"));
    }
}