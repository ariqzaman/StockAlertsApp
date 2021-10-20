package com.example.capstoneproject.fragments.portfolio;

public class portfoliostock {
    private String stockname;
    private String stockprice;

    portfoliostock(String stockname,String stockprice){
        this.stockname = stockname;
        this.stockprice = stockprice;
    }
    public String getStockname(){
        return stockname;
    }

    public String getStockPrice(){
        return stockprice;
    }
    public void setStockname(String stockname){
        this.stockname = stockname;
    }
    public void setStockPrice(String stockprice){
        this.stockprice = stockprice;
    }


}
