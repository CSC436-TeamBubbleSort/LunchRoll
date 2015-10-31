package com.csc436.team_bubble_sort.lunchroll.model.user;

import android.graphics.Point;

import com.csc436.team_bubble_sort.lunchroll.model.group.Groups;
import com.csc436.team_bubble_sort.lunchroll.model.group.UserGroup;
import com.csc436.team_bubble_sort.lunchroll.model.locations.ClientNearbyAny;
import com.csc436.team_bubble_sort.lunchroll.model.preferences.Preferences;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Devin on 10/31/2015.
 */
public class AppUser implements Preferences, Comparable<AppUser>, ClientNearbyAny, Users, Groups {
    private Preferences preferences;
    private ArrayList<UserGroup> userGroups;
    private int userID;
    private String email;
    private BigDecimal phoneNumber;
    private Point location;

    public AppUser(){

    }


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
    public int compareTo(AppUser another) {
        return 0;
    }

    @Override
    public String toString(){
        return "";
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
