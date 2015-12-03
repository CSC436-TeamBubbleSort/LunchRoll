package com.csc436.team_bubble_sort.lunchroll.entities;

import android.media.Image;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jonathan on 11/15/2015.
 */
public class Restaurant {
    private long latitude;
    private long longitude;
    private String name;
    private String priceLevel;
    private String rating;
    private String picture;

    public Restaurant(JSONObject result){
        if(result == null){
            Log.e("restaurant", "Trying to parse null result");
        }
        try {
            JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
            latitude = location.getLong("lat");
            longitude = location.getLong("lng");
            name = result.getString("name");
            priceLevel = result.optString("price_level");
            rating = result.optString("rating");
//            JSONArray pics = result.optJSONArray("photos");
//            if(pics != null) {
//                JSONObject pic = pics.getJSONObject(0);
//                picture = new Image();
//            }
        } catch (JSONException e) {
            Log.e("restaurant", "Error parsing JSON in Restaurant constructor");
            e.printStackTrace();
        }
    }

    // Used for making hard coded restaurants
    public Restaurant(long latitude, long longitude, String name, String priceLevel,
                      String rating, String picture){
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.priceLevel = priceLevel;
        this.rating = rating;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setLatitude(long latitude){
        this.latitude = latitude;
    }

    public long getLatitude(){
        return latitude;
    }

    public void setLongitude(long longitude){
        this.longitude = longitude;
    }

    public long getLongitude(){
        return longitude;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
