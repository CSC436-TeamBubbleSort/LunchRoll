package com.csc436.team_bubble_sort.lunchroll.web_services.location;

/**
 * Created by jon on 11/5/2015.
 */
public interface NearbyAny {
    public void nearbyAnyRequest();
    public void nearbyAnySuccess(String result);
    public void nearbyAnyError(String error);
}
