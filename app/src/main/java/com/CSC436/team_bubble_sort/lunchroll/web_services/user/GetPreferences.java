package com.csc436.team_bubble_sort.lunchroll.web_services.user;


import com.csc436.team_bubble_sort.lunchroll.entities.Preferences;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface GetPreferences {
    public void getPreferencesRequest(int userId);
    public void getPreferencesSuccess(boolean success, Preferences preferences);
    public void getPreferencesError(String error);
}
