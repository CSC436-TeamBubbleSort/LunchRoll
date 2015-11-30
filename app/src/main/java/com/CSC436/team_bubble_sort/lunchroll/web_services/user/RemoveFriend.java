package com.csc436.team_bubble_sort.lunchroll.web_services.user;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface RemoveFriend {
    public void removeFriendRequest(int userId, int friendUserId);
    public void removeFriendSuccess(String response);
    public void removeFriendError(String error);
}
