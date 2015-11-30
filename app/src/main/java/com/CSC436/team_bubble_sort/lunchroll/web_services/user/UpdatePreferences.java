package com.csc436.team_bubble_sort.lunchroll.web_services.user;

import com.csc436.team_bubble_sort.lunchroll.entities.Preferences;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface UpdatePreferences {
    public void updatePreferencesRequest(int userId);
    public void updatePreferencesSuccess(String responses);
    public void updatePreferencesError(String error);
}
