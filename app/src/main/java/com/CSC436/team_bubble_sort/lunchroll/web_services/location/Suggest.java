package com.csc436.team_bubble_sort.lunchroll.web_services.location;

import com.csc436.team_bubble_sort.lunchroll.entities.Restaurant;

import java.util.List;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface Suggest {

    public void suggestRequest(int userId, int groupId, double latitude, double longitude);
    public void suggestSuccess(List<Restaurant> restaurants);
    public void suggestError(String error);
}
