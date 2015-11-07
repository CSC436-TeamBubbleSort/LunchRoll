package com.csc436.team_bubble_sort.lunchroll.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jonathan on 11/6/2015.
 */
public class Group {

    private int groupId = 0;
    private int userId;
    private String name;
    private String users;

    public Group(int userId, String name, String users){
        this.userId = userId;
        this.name = name;
        this.users = users;
    }

    public Group(int groupId, int userId, String name, String users){
        this.groupId = groupId;
        this.userId = userId;
        this.name = name;
        this.users = users;
    }

    public int getGroupId(){
        return this.groupId;
    }

    public int getUserId(){
        return this.userId;
    }

    public String getName(){
        return this.name;
    }

    public List<Integer> getUsers(){
        List<Integer> numbersInt = new ArrayList<>();
        for (String number : Arrays.asList(this.users.split("\\s*,\\s*"))){
            numbersInt.add(Integer.valueOf(number));
        }
        return numbersInt;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUsers(List<Integer> users){
        this.users = android.text.TextUtils.join(",", users);
    }


}
