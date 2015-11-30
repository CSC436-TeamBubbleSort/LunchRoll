package com.csc436.team_bubble_sort.lunchroll.web_services.location;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface Search {
    public void searchRequest(double latitude, double longitude);
    public void searchSuccess(/* List of restaurantResult objects */);
    public void searchError(String error);
}
