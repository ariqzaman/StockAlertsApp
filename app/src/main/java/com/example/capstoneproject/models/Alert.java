package com.example.capstoneproject.models;

public class Alert {
    private String symbol;
    private String name;
    private double currentPrice;
    private double alertPrice;

    public Alert(String symbol, String name, double currentPrice, double alertPrice) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
        this.alertPrice = alertPrice;
    }

    //test method for changing text on recyclerview items
    public void changeText(String text) {
        this.name = text;
    }



    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getAlertPrice() {
        return alertPrice;
    }




    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setAlertPrice(double alertPrice) {
        this.alertPrice = alertPrice;
    }
}


