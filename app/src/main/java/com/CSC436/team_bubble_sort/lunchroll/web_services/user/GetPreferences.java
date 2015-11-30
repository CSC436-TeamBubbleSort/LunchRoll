package com.csc436.team_bubble_sort.lunchroll.web_services.user;


import com.csc436.team_bubble_sort.lunchroll.entities.Preferences;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface GetPreferences {
    public void getUserRequest(int userId);
    public void getUserSuccess(Preferences preferences);
    public void getUserError(String error);
}
