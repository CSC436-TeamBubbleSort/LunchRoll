package com.csc436.team_bubble_sort.lunchroll.entities;

/**
 * Created by Jonathan on 11/27/2015.
 */
public class GroupListItem {
    private int groupId;
    private String name;

    public GroupListItem(int groupId, String name){
        this.groupId = groupId;
        this.name = name;
    }

    public void setGroupId(int groupId){this.groupId = groupId;}
    public int getGroupId(){return groupId;}
    public void setName(String name){this.name = name;}
    public String getName(){return name;}
}
