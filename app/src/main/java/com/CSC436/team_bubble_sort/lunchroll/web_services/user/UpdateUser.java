package com.csc436.team_bubble_sort.lunchroll.web_services.user;

/**
 * Created by jon on 11/5/2015.
 */
public interface UpdateUser {
    public void updateUserRequest();
    public void updateUserSuccess(String result);
    public void updateUserError(String error);
}
