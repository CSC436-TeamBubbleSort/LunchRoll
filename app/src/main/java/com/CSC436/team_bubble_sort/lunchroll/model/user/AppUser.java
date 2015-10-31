package com.csc436.team_bubble_sort.lunchroll.model.user;

import android.graphics.Point;

import com.csc436.team_bubble_sort.lunchroll.model.group.GroupsCalls;
import com.csc436.team_bubble_sort.lunchroll.model.group.UserGroup;
import com.csc436.team_bubble_sort.lunchroll.model.locations.ClientNearbyAnyCalls;
import com.csc436.team_bubble_sort.lunchroll.model.preferences.PreferencesCalls;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Devin on 10/31/2015.
 */
public class AppUser implements PreferencesCalls, Comparable<AppUser>, ClientNearbyAnyCalls, UsersCalls, GroupsCalls {
    private PreferencesCalls preferences;
    private ArrayList<UserGroup> userGroups;
    private int userID;
    private String email;
    private BigDecimal phoneNumber;
    private Point location;

    public AppUser(int userID, PreferencesCalls preferences, ArrayList<UserGroup> userGroups){
        this.userID = userID;
        this.preferences = preferences;
        this.userGroups = userGroups;
    }

    @Override
    public String toString(){
        return null;
    }

    @Override
    public int compareTo(AppUser another) {
        if (this.userID < another.userID) return -1;
        else if(this.userID > another.userID) return 1;
        else return 0;
    }

    // Getters and Setters
    public PreferencesCalls getPreferences(){return preferences;}

    public void setPreferences(PreferencesCalls newPreferences){preferences = newPreferences;}

    public ArrayList<UserGroup> getUserGroups(){return userGroups;}

    public void setUserGroups(ArrayList<UserGroup> newUserGroups){userGroups = newUserGroups;}

    public int getUserID(){return userID;}

    public void setUserID(int newUserID){userID = newUserID;}

    // TODO Web Service Calls
    @Override
    public void savePreferenceList() {

    }

    @Override
    public void loadPreferenceList() {

    }

    @Override
    public void clientNearbyAnyRequest() {

    }

    @Override
    public void clientNearbyAnySuccess() {

    }

    @Override
    public String clientNearbyAnyError() {
        return null;
    }

    @Override
    public void saveUser() {

    }

    @Override
    public void loadUser() {

    }

    @Override
    public void saveGroup() {

    }

    @Override
    public void loadGroup() {

    }

    @Override
    public void saveGroups() {

    }

    @Override
    public void loadGroups() {

    }
}
