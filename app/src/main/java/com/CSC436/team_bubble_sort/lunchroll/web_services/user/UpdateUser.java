package com.csc436.team_bubble_sort.lunchroll.web_services.user;

import com.csc436.team_bubble_sort.lunchroll.entities.User;

/**
 * Created by jon on 11/5/2015.
 */
public interface UpdateUser {
    public void updateUserRequest(User user);
    public void updateUserSuccess(User user);
    public void updateUserError(String error);
}
