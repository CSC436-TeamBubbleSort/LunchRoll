package com.csc436.team_bubble_sort.lunchroll.model.user;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

import com.csc436.team_bubble_sort.lunchroll.model.group.GroupsCalls;
import com.csc436.team_bubble_sort.lunchroll.model.group.UserGroup;
import com.csc436.team_bubble_sort.lunchroll.model.locations.ClientNearbyAnyCalls;
import com.csc436.team_bubble_sort.lunchroll.model.preferences.CategoryOfFoodPreferences;
import com.csc436.team_bubble_sort.lunchroll.model.preferences.PreferencesCalls;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.UpdateUser;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

//TODO implement Parcelable instead of Serializable for performance boost
public class AppUser implements Comparable<AppUser>, Serializable {
    private CategoryOfFoodPreferences preferences;
    private String userID;
    private ArrayList<UserGroup> userGroups;
    private String username;
    private String password;
    private ArrayList<AppUser> friendsList;
    private String email;
    private BigDecimal phoneNumber;
    private Point location;

    public AppUser(String username, CategoryOfFoodPreferences preferences){
        this.userID = "0";
        this.username = username;
        this.preferences = preferences;
        this.userGroups = new ArrayList<>();
        this.friendsList = new ArrayList<>();
    }

    public ArrayList<String> getGroupNames(){
        ArrayList<String> groupNames = new ArrayList<>();
        for(UserGroup group : userGroups){
            groupNames.add(group.getGroupName());
        }
        return groupNames;
    }

    public ArrayList<String> getFriendsNames(){
        ArrayList<String> friends = new ArrayList<>();
        for(AppUser friend : friendsList){
            friends.add(friend.getUsername());
        }
        return friends;
    }

    // TODO Helps build JSON object in string form
    @Override
    public String toString(){
        JSONObject json_appuser = new JSONObject();
        JSONArray json_usergroups = new JSONArray();
        try {
            json_appuser.accumulate("name", username);
            json_appuser.accumulate("preferences", preferences.toString());
            for(UserGroup group : userGroups){
                json_usergroups.put(group.toString());
            }
           json_appuser.accumulate("user_groups", json_usergroups);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json_appuser.toString();
    }
    // Allows a list of appUsers to be compared
    @Override
    public int compareTo(AppUser another) {
        if (this.username.compareTo(another.username) < 0) return -1;
        else if(this.username.compareTo(another.username) > 0) return 1;
        else return 0;
    }

    // Getters and Setters
    public CategoryOfFoodPreferences getPreferences(){return preferences;}
    public void setPreferences(CategoryOfFoodPreferences newPreferences){preferences = newPreferences;}
    public ArrayList<UserGroup> getUserGroups(){return userGroups;}
    public void setUserGroups(ArrayList<UserGroup> newUserGroups){userGroups = newUserGroups;}
    public String getUsername(){return username;}
    public void setUsername(String newUserID){username = newUserID;}
    public void setFriendsList(ArrayList<AppUser> newFriendsList){
        friendsList = newFriendsList;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public ArrayList<AppUser> getFriendsList(){
        return friendsList;
    }

    public JSONObject toJSON(){
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("username", username);
            jsonBody.accumulate("password", password);
            jsonBody.accumulate("userID", userID);
            jsonBody.accumulate("email", email);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody;
        //return "{\"email\":" + email + ", \"password\":" + password + ", \"username\":" + username + ", \"userID\": \"" + userID + "\"}";
    }

}
