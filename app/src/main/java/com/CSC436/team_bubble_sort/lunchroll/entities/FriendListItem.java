package com.csc436.team_bubble_sort.lunchroll.entities;

/**
 * Created by jon on 11/30/2015.
 */
public class FriendListItem {
    private int friendId;
    private String username;
    private String email;

    public FriendListItem(int friendId, String username, String email){
        setFriendId(friendId);
        setUsername(username);
        setEmail(email);
    }

    public void setFriendId(int friendId){
        this.friendId = friendId;
    }

    public int getFriendId(){
        return friendId;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }
}
