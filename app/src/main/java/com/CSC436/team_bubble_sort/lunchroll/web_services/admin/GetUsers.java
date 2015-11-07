package com.csc436.team_bubble_sort.lunchroll.web_services.admin;

/**
 * Created by Jonathan on 11/5/2015.
 */
public interface GetUsers {
    public void getUsersRequest();
    public void getUsersSuccess(String result);
    public void getUsersError(String error);
}
