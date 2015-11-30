package com.csc436.team_bubble_sort.lunchroll.web_services.user;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface GetFriends {
    public void getFriendsRequest(int userId);
    public void getFriendsSuccess(String responses);
    public void getFriendsError(String error);
}
