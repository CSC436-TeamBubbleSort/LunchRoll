package com.csc436.team_bubble_sort.lunchroll.web_services.group;

import com.csc436.team_bubble_sort.lunchroll.entities.Group;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface GetGroup {
    void getGroupRequest(int groupId);
    void getGroupSuccess(Group group);
    void getGroupError(String error);
}
