package com.csc436.team_bubble_sort.lunchroll.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group implements Comparable<Group>, Serializable{
    private int userId, groupId;
    private String name, users;
    // Constructor
    public Group(int userId, String name, List<Integer> users){
        this.userId = userId;
        this.name = name;
        setUserList(users);
        groupId = 0;
    }

    public Group(int userId, String name, String users, int groupId){
        this.userId = userId;
        this.name = name;
        this.users = users;
        this.groupId = groupId;
    }

    // Getters and Setters
    public void setUserId(int userId){this.userId = userId;}
    public int getUserId(){return userId;}
    public void setGroupId(int groupId){this.groupId = groupId;}
    public int getGroupId(){return groupId;}
    public void setName(String name){this.name = name;}
    public String getName(){return name;}
    public void setUsers(String users){this.users = users;}
    public String getUsers(){return users;}
    public void setUserList(List<Integer> users){
        this.users = android.text.TextUtils.join(", ", users);
    }
    public List<Integer> getUserList(){
        List<Integer> numbersInt = new ArrayList<>();
        for (String number : Arrays.asList(this.users.split("\\s*,\\s*"))){
            numbersInt.add(Integer.valueOf(number));
        }
        return numbersInt;
    }
    // Allows sorting a list of groups by name alphabetically
    @Override
    public int compareTo(Group another) {
        if(this.name.compareTo(another.name) < 0) return -1;
        else if(this.name.compareTo(another.name) > 0) return 1;
        else return 0;
    }
}