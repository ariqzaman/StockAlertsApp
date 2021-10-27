package com.example.capstoneproject.fragments.portfolio;

public class portfoliostock {
    private String stockname;
    private String stockprice;
    private int stockamount;

    portfoliostock(String stockname,String stockprice, int stockamount){
        this.stockname = stockname;
        this.stockprice = stockprice;
        this.stockamount = stockamount;
    }
    public String getStockname(){
        return stockname;
    }

    public String getStockPrice(){
        return stockprice;
    }

    public int getStockamount() {return stockamount;}
    public void setStockname(String stockname){
        this.stockname = stockname;
    }
    public void setStockPrice(String stockprice){
        this.stockprice = stockprice;
    }
    public void setStockamount(int stockamount){this.stockamount = stockamount;}



}
