package com.csc436.team_bubble_sort.lunchroll.entities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jonathan on 11/15/2015.
 */
public class Restaurant {
    private long latitude;
    private long longitude;
    private String iconURL;
    private String name;
    private String priceLevel;
    private String rating;
    private String crossRoads; //Vicinity in the object
    private boolean openNow;

    public Restaurant(JSONObject result){
        if(result == null){
            Log.e("restaurant", "Trying to parse null result");
        }
        try {
            JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
            latitude = location.getLong("lat");
            longitude = location.getLong("lng");
            iconURL = result.optString("icon");
            name = result.getString("name");
            priceLevel = result.optString("price_level");
            rating = result.optString("rating");
            crossRoads = result.optString("vicinity");
            if (result.optJSONObject("opening_hours") != null){
                openNow = result.optJSONObject("opening_hours").optBoolean("open_now");
            }

        } catch (JSONException e) {
            Log.e("restaurant", "Error parsing JSON in Restaurant constructor");
            e.printStackTrace();
        }
    }

    // Used for making hard coded restaurants
    public Restaurant(long latitude, long longitude, String iconURL, String name,
                      String priceLevel, String rating, String crossRoads, boolean openNow){
        this.latitude = latitude;
        this.longitude = longitude;
        this.iconURL = iconURL;
        this.name = name;
        this.priceLevel = priceLevel;
        this.rating = rating;
        this.crossRoads = crossRoads;
        this.openNow = openNow;
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

    public String getCrossRoads() {
        return crossRoads;
    }

    public void setCrossRoads(String crossRoads) {
        this.crossRoads = crossRoads;
    }

    public boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }
}
