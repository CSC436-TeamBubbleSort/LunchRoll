package com.csc436.team_bubble_sort.lunchroll.web_services.user;

import com.csc436.team_bubble_sort.lunchroll.entities.User;

/**
 * Created by Jonathan on 11/5/2015.
 */
public interface Login {
    public void loginRequest();
    public void loginSuccess(boolean success, String errorMessage,User user);
    public void loginError(String error);
}