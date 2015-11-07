package com.csc436.team_bubble_sort.lunchroll.entities;

/**
 * Created by Jonathan on 11/6/2015.
 */
public class User {

    private int userId = 0;
    private String username;
    private String password;
    private String email;

    public User(int userId, String username, String password, String email){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getUserId(){
        return this.userId;
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
       return this.email;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
