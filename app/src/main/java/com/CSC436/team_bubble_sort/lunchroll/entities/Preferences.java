package com.csc436.team_bubble_sort.lunchroll.entities;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Preferences implements Serializable{
    private ArrayList<Preference> preferences;
    private int userId;
    // Constructor
    public Preferences(int userId, JSONObject newPreferences){
        this.userId = userId;
        preferences = new ArrayList<>();
        String [] serverPreferences = new String [] {"american", "asian", "greek", "indian", "italian", "mexican"};
        try {
            for(int i = 0; i < serverPreferences.length; i++){
                preferences.add(new Preference(serverPreferences[i], newPreferences.getInt(serverPreferences[i]) == 1 ? true : false));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(preferences);
    }
    // Simple Getter
    public int getUserId(){return userId;}
    // Maintaining preferences list
    public void flipPreferenceValue(String key){
        Preference pref = findPreference(key);
        if(pref == null)return;
        if(pref.pref_value) pref.pref_value = false;
        else pref.pref_value = true;
    }
    // This is used for populating a ListView with the names of each type of food
    public List<String> getPreferenceNames(){
        ArrayList<String> listOfFoodTypes = new ArrayList<>();
        for(Preference foodType : preferences){
            listOfFoodTypes.add(foodType.pref_name);
        }
        Collections.sort(listOfFoodTypes);
        return listOfFoodTypes;
    }
    // Used for flipping a preference value
    // Currently just a O(n) search as preference list is small
    // I want a null point exception if key is not found because that means we have an error
    // somewhere else and should stop.
    private Preference findPreference(String key){
        for(Preference pref : preferences){
            if(pref.pref_name.compareTo(key) == 0){
                return pref;
            }
        }
        return null;
    }

    // Private class declaring a Preference
    // Provides simple key:value pair object with comparable keys for alphabetical order
    private class Preference implements Comparable<Preference>, Serializable{
        private String pref_name;
        private boolean pref_value; // is it checked or not
        // Constructor
        public Preference(String newThing, boolean newUserApprovalSetting){
            pref_name = newThing;
            pref_value = newUserApprovalSetting;
        }
        // Allows sorting a list of Preferences
        @Override
        public int compareTo(@NonNull Preference object){
            return this.pref_name.compareTo(object.pref_name);
        }
    }
}
