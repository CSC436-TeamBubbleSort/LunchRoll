package com.csc436.team_bubble_sort.lunchroll.web_services.user;

/**
 * Created by Jonathan on 11/5/2015.
 */
public interface Login {
    public void loginRequest();
    public void loginSuccess(String result);
    public void loginError(String error);
}