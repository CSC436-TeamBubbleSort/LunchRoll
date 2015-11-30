package com.csc436.team_bubble_sort.lunchroll.web_services.group;

import com.csc436.team_bubble_sort.lunchroll.entities.Group;
import com.csc436.team_bubble_sort.lunchroll.entities.GroupListItem;

import java.util.List;

/**
 * Created by jon on 11/5/2015.
 */
public interface GetGroups {
    void getGroupsRequest(int userId);
    void getGroupsSuccess(List<GroupListItem> groupListItems);
    void getGroupsError(String error);
}
