package com.csc436.team_bubble_sort.lunchroll.model.preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryOfFoodPreferences{
    private ArrayList<CategoryOfFoodPreference> preferences;
    // Constructor
    public CategoryOfFoodPreferences(){
        preferences = new ArrayList<>();
        addDefaultTypesOfFood();
    }
    // Maintaining preferences list
    public void flipPreferenceValue(String key){
        CategoryOfFoodPreference pref = findPreference(key);
        if(pref.userApproved == true) pref.userApproved = false;
        else pref.userApproved = true;
    }
    // Used for flipping a preference value
    // Currently just a O(n) search as preference list is small
    // I want a null point exception if key is not found because that means we have an error
    // somewhere else and should stop.
    private CategoryOfFoodPreference findPreference(String key){
        for(CategoryOfFoodPreference pref : preferences){
            if(pref.categoryOfFood.compareTo(key) == 0){
                return pref;
            }
        }
        return null;
    }
    // TODO Should this list should be maintained on server?
    // This provides a list of defaults types of food with default preferences.
    // The number of keys won't increase unless we update, but true/false values can change.
    private void addDefaultTypesOfFood(){
        preferences.add(new CategoryOfFoodPreference("Mexican", false));
        preferences.add(new CategoryOfFoodPreference("Asian", false));
        preferences.add(new CategoryOfFoodPreference("American", false));
        preferences.add(new CategoryOfFoodPreference("Italian", false));
        preferences.add(new CategoryOfFoodPreference("Indian", false));
        Collections.sort(preferences);
    }
    // This is used for populating a ListView with the names of each type of food
    public List<String> getFoodTypesOnly(){
        ArrayList<String> listOfFoodTypes = new ArrayList<>();
        for(CategoryOfFoodPreference foodType : preferences){
            listOfFoodTypes.add(foodType.categoryOfFood);
        }
        Collections.sort(listOfFoodTypes);
        return listOfFoodTypes;
    }
    // Helps build JSON object
    @Override
    public String toString(){
        String result = "{";
        for(CategoryOfFoodPreference foodType : preferences){
            result += foodType.toString() + ", ";
        }
        return result.substring(0,result.length() - 2) + "}";
    }

    // Private class declaring a CategoryOfFoodPreference
    // Provides simple key:value pair object with comparable keys and JSON toString helper
    private class CategoryOfFoodPreference implements Comparable<CategoryOfFoodPreference>{
        private String categoryOfFood;
        private boolean userApproved;
        // Constructor
        public CategoryOfFoodPreference(String newThing, boolean newUserApprovalSetting){
            categoryOfFood = newThing;
            userApproved = newUserApprovalSetting;
        }
        // Allows sorting a list of CategoryOfFoodPreferences
        @Override
        public int compareTo(CategoryOfFoodPreference o) {
            return this.categoryOfFood.compareTo(o.categoryOfFood);
        }
        // Helps build JSON object
        @Override
        public String toString(){return "\"" + categoryOfFood + "\":" + userApproved;}
    }
}
