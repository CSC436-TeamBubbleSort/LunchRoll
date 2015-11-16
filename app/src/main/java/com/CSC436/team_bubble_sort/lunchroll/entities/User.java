package com.csc436.team_bubble_sort.lunchroll.entities;

import android.graphics.Point;
import android.support.annotation.NonNull;

import com.csc436.team_bubble_sort.lunchroll.entities.Group;
import com.csc436.team_bubble_sort.lunchroll.entities.Preferences;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

//TODO implement Parcelable instead of Serializable for performance boost
public class User implements Comparable<User>, Serializable {
    private Preferences preferences;
    private ArrayList<Group> groups;
    private ArrayList<User> friendsList;
    public int userId;
    public String username, password, email;
    private Point location;

    public User(int userId, String username, String password, String email){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        // Set Defaults
        // TODO make call to get preferences
        // I'm assuming we do this by saving user and reloading user
        // so the server has a chance to populate preferences list
        this.preferences = null;
        this.groups = new ArrayList<>();
        this.friendsList = new ArrayList<>();
        this.location = new Point(0,0);
    }

    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        // Set Defaults
        this.userId = 0;
        // TODO make call to get preferences
        // I'm assuming we do this by saving user and reloading user
        // so the server has a chance to populate preferences list
        this.preferences = null;
        this.groups = new ArrayList<>();
        this.friendsList = new ArrayList<>();
        this.location = new Point(0,0);
    }

    // Used for populating ListView of groups
    public ArrayList<String> getGroupNames(){
        ArrayList<String> groupNames = new ArrayList<>();
        for(Group group : groups){
            groupNames.add(group.getName());
        }
        return groupNames;
    }

    // Used for populating ListView of friends
    public ArrayList<String> getFriendsNames(){
        ArrayList<String> friends = new ArrayList<>();
        for(User friend : friendsList){
            friends.add(friend.getUsername());
        }
        return friends;
    }

    // Allows a list of Users to be compared so their names are displayed alphabetically
    // Useful for ListView of friends
    @Override
    public int compareTo(@NonNull User another) {
        if (this.username.compareTo(another.username) < 0) return -1;
        else if(this.username.compareTo(another.username) > 0) return 1;
        else return 0;
    }

    // Getters and Setters
    public Preferences getPreferences(){return preferences;}
    public void setPreferences(Preferences newPreferences){preferences = newPreferences;}
    public ArrayList<Group> getGroups(){return groups;}
    public void setGroups(ArrayList<Group> groups){this.groups = groups;}
    public ArrayList<User> getFriendsList(){return friendsList;}
    public void setFriendsList(ArrayList<User> newFriendsList){friendsList = newFriendsList;}
    public String getUsername(){return username;}
    public void setUsername(String newUserID){username = newUserID;}
    public String getEmail(){return this.email;}
    public void setEmail(String email){this.email = email;}
    public String getPassword(){return this.password;}
    public void setPassword(String password){this.password = password;}
    public int getUserId(){return userId;}

    public JSONObject toJSON(){
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("username", username);
            jsonBody.accumulate("password", password);
            jsonBody.accumulate("userId", userId);
            jsonBody.accumulate("email", email);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody;
    }
}
