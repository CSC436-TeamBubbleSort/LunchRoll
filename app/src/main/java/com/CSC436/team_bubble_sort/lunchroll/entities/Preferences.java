package com.csc436.team_bubble_sort.lunchroll.entities;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Preferences implements Serializable {
    private ArrayList<Preference> preferences;
    private int userId;
    String[] serverPreferences;

    // Constructor
    public Preferences(int userId, JSONObject newPreferences) {
        this.userId = userId;
        preferences = new ArrayList<>();
        serverPreferences = new String[]{"american", "asian", "greek", "indian", "italian", "mexican"};

            for (int i = 0; i < serverPreferences.length; i++) {
                preferences.add(new Preference(serverPreferences[i], newPreferences.optString(serverPreferences[i]).compareToIgnoreCase("1") == 0 ? true : false));
            }

        Collections.sort(preferences);
    }

    public Preferences(int userId) {
        this.userId = userId;
        preferences = new ArrayList<>();
        serverPreferences = new String[]{"american", "asian", "greek", "indian", "italian", "mexican"};
        for (int i = 0; i < serverPreferences.length; i++) {
            preferences.add(new Preference(serverPreferences[i], false));
        }
        Collections.sort(preferences);
    }

    // Simple Getter
    public int getUserId() {
        return userId;
    }

    // Maintaining preferences list
    public void flipPreferenceValue(String key) {
        Preference pref = findPreference(key);
        if (pref == null) return;
        if (pref.pref_value) pref.pref_value = false;
        else pref.pref_value = true;
    }

    public void setPreferenceValue(String key, boolean value) {
        Preference pref = findPreference(key);
        //if (pref == null) return;
        pref.pref_value = value;
    }

    public boolean getPreferenceValue(String key) {
        Preference pref = findPreference(key);
        //if (pref == null) return false;
        return pref.pref_value;
    }

    // This is used for populating a ListView with the names of each type of food
    public List<String> getPreferenceNames() {
        ArrayList<String> listOfFoodTypes = new ArrayList<>();
        for (Preference foodType : preferences) {
            listOfFoodTypes.add(foodType.pref_name);
        }
        Collections.sort(listOfFoodTypes);
        return listOfFoodTypes;
    }

    // Used for flipping a preference value
    // Currently just a O(n) search as preference list is small
    // I want a null point exception if key is not found because that means we have an error
    // somewhere else and should stop.
    private Preference findPreference(String key) {
        for (Preference pref : preferences) {
            if (pref.pref_name.compareToIgnoreCase(key) == 0) {
                return pref;
            }
        }
        return null;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("userId", getUserId() + "");
            for (int i = 0; i < serverPreferences.length; i++) {
                obj.put(serverPreferences[i], (getPreferenceValue(serverPreferences[i])) ? "1" : "0");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    // Private class declaring a Preference
    // Provides simple key:value pair object with comparable keys for alphabetical order
    private class Preference implements Comparable<Preference>, Serializable {
        public String pref_name;
        public boolean pref_value; // is it checked or not

        // Constructor
        public Preference(String newThing, boolean newUserApprovalSetting) {
            pref_name = newThing;
            pref_value = newUserApprovalSetting;
        }

        // Allows sorting a list of Preferences
        @Override
        public int compareTo(@NonNull Preference object) {
            return this.pref_name.compareTo(object.pref_name);
        }
    }
}
