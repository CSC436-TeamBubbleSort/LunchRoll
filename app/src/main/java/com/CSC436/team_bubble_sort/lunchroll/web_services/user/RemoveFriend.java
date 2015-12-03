package com.csc436.team_bubble_sort.lunchroll.web_services.user;

import com.csc436.team_bubble_sort.lunchroll.entities.Friend;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface RemoveFriend {
    public void removeFriendRequest(int userId, int friendId);
    public void removeFriendSuccess(int friendId);
    public void removeFriendError(String error);
}
