package com.csc436.team_bubble_sort.lunchroll.web_services.user;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface AddFriend {
    public void addFriendRequest(int userId, String username, String emailAddress);
    public void addFriendSuccess(String response);
    public void addFriendError(String error);
}
