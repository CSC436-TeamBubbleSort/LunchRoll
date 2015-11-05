package com.csc436.team_bubble_sort.lunchroll.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class UserGroup implements Comparable<UserGroup>, Serializable{
    private String groupName;
    private String parentUsername; // User that created this group
    private ArrayList<AppUser> appUsers;
    // Constructor
    public UserGroup(String newGroupName, String newParentUserID){
        groupName = newGroupName;
        parentUsername = newParentUserID;
        appUsers = new ArrayList<>();
    }

    @Override
    public String toString(){
        JSONObject json_group = new JSONObject();
        JSONArray json_users = new JSONArray();
        try {
            json_group.accumulate("name", groupName);
            json_group.accumulate("user", parentUsername);
            for(AppUser user : appUsers){
                json_users.put(user.toString());
            }
            json_group.accumulate("users", json_users);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json_group.toString();
    }

    // Getters and Setters
    public void setGroupName(String newGroupName){groupName = newGroupName;}
    public String getGroupName(){return groupName;}
    public void setParentUsername(String newParentUserID){parentUsername = newParentUserID;}
    public String getParentUsername(){return parentUsername;}
    public ArrayList<AppUser> getAppUsers(){return appUsers;}
    public void setAppUsers(ArrayList<AppUser> newAppUserList){appUsers = newAppUserList;}
    // Maintaining appUsers list
    public void addAppUser(AppUser newAppUser){appUsers.add(newAppUser);}
    public void deleteAppUser(AppUser appUserToDelete){appUsers.remove(appUserToDelete);}
    public void sortAppUsers(){Collections.sort(appUsers);}
    // Allows sorting a list of groups by name
    @Override
    public int compareTo(UserGroup another) {
        if(this.groupName.compareTo(another.groupName) < 0) return -1;
        else if(this.groupName.compareTo(another.groupName) > 0) return 1;
        else return 0;
    }
}
