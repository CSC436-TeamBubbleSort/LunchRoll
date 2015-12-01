package com.csc436.team_bubble_sort.lunchroll.web_services.user;

import com.csc436.team_bubble_sort.lunchroll.entities.FriendListItem;

import java.util.List;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface GetFriends {
    public void getFriendsRequest(int userId);
    public void getFriendsSuccess(List<FriendListItem> friends);
    public void getFriendsError(String error);
}
