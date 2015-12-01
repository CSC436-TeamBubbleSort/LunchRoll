package com.csc436.team_bubble_sort.lunchroll.entities;

/**
 * Created by jon on 11/30/2015.
 */
public class Friend {
    private int friendId;
    private int userId; //userId that this friend belongs too.
    private String username;
    private String email;

    public Friend(int userId, String username, String email){
        setFriendId(0);
        setUserId(userId);
        setUsername(username);
        setEmail(email);
    }

    public Friend(int friendId, int userId, String username, String email){
        setFriendId(friendId);
        setUserId(userId);
        setUsername(username);
        setEmail(email);
    }

    public void setFriendId(int friendId){
        this.friendId = friendId;
    }

    public int getFriendId(){
        return friendId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getUserId(){
        return userId;
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
