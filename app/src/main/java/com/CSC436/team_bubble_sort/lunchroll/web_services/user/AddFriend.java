package com.csc436.team_bubble_sort.lunchroll.web_services.user;

import com.csc436.team_bubble_sort.lunchroll.entities.Friend;
import com.csc436.team_bubble_sort.lunchroll.entities.FriendListItem;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface AddFriend {
    public void addFriendRequest(Friend friend);
    public void addFriendSuccess(FriendListItem friendListItem);
    public void addFriendError(String error);
}
