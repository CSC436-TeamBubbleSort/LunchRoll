package com.csc436.team_bubble_sort.lunchroll.model.group;

import com.csc436.team_bubble_sort.lunchroll.model.user.AppUser;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Devin on 10/31/2015.
 */
public class UserGroup implements Comparable<UserGroup>{
    private int groupID;
    private int parentUserID;
    private ArrayList<AppUser> appUsers;

    public UserGroup(){

    }

    public void addAppUser(AppUser newAppUser){
        appUsers.add(newAppUser);
    }

    public void deleteAppUser(AppUser appUserToDelete){
        appUsers.remove(appUserToDelete);
    }

    public void sortAppUsers(){
        Collections.sort(appUsers);
    }

    public ArrayList<AppUser> getAppUsers(){
        return appUsers;
    }

    public void setAppUsers(ArrayList<AppUser> newAppUserList){
        appUsers = newAppUserList;
    }

    @Override
    public int compareTo(UserGroup another) {
        if(this.groupID > another.groupID) return 1;
        else if(this.groupID < another.groupID) return -1;
        else return 0;
    }
}
