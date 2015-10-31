package com.csc436.team_bubble_sort.lunchroll.model.preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// This class holds everything that needs to be known about a particular set of preferences
public class CategoryOfFoodPreferences{
    private ArrayList<CategoryOfFoodPreference> preferences;

    public CategoryOfFoodPreferences(){
        preferences = new ArrayList<>();
        addDefaultTypesOfFood();
    }

    // TODO ability to set and update preferences

    // TODO Should this list should be maintained on server?
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

    @Override
    public String toString(){
        String result = "{";
        for(CategoryOfFoodPreference foodType : preferences){
            result += foodType.toString() + ", ";
        }
        return result.substring(0,result.length() - 2) + "}";
    }

    // Private class of individual preferences. I just wanted a key:value pair object where
    // keys can be compared, and that has a useful toString
    private class CategoryOfFoodPreference implements Comparable<CategoryOfFoodPreference>{
        private String categoryOfFood;
        private boolean userApproved;

        public CategoryOfFoodPreference(String newThing, boolean newUserApprovalSetting){
            categoryOfFood = newThing;
            userApproved = newUserApprovalSetting;
        }

        @Override
        public int compareTo(CategoryOfFoodPreference o) {
            return this.categoryOfFood.compareTo(o.categoryOfFood);
        }

        @Override
        public String toString(){return "\"" + categoryOfFood + "\":" + userApproved;}
    }
}
