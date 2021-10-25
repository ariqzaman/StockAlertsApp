package com.example.capstoneproject.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//@Parcel
public class Article {
    String thumbnailPath;
    String title;
    String text;
    String tickers;
    String sentiment;
    String newsLink;

    //take in json object and read out the fields we need
    public Article(JSONObject jsonObject) throws JSONException {
        thumbnailPath = jsonObject.getString("image_url");
        title = jsonObject.getString("title");
        text = jsonObject.getString("text");
        sentiment = jsonObject.getString("sentiment");
        newsLink = jsonObject.getString("news_url");
        tickers = jsonObject.getString("tickers");
    }

    public Article(){}

    public static List<Article> fromJsonArray(JSONArray articleJsonArray) throws JSONException {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < articleJsonArray.length(); i++) {
            articles.add(new Article(articleJsonArray.getJSONObject(i)));
        }
        return articles;
    }

    public String getPosterPath() {
        return thumbnailPath;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getSentiment(){
        return sentiment;
    }

    public String getLink(){
        return newsLink;
    }

    public String getTickers(){
        return tickers;
    }

}