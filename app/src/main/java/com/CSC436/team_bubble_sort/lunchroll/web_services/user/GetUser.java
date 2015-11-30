package com.csc436.team_bubble_sort.lunchroll.web_services.user;

import com.csc436.team_bubble_sort.lunchroll.entities.User;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface GetUser {
    public void getUserRequest(int userId);
    public void getUserSuccess(User user);
    public void getUserError(String error);
}
