package com.csc436.team_bubble_sort.lunchroll.model.group;

import com.csc436.team_bubble_sort.lunchroll.model.user.AppUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class UserGroup implements Comparable<UserGroup>, Serializable{
    private static final long serialVersionUID = 20L;
    private String groupName;
    private String parentUsername;
    private ArrayList<AppUser> appUsers;
    // Constructor
    public UserGroup(String newGroupName, String newParentUserID){
        groupName = newGroupName;
        parentUsername = newParentUserID;
        appUsers = new ArrayList<>();
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
