package com.csc436.team_bubble_sort.lunchroll.web_services.group;

import com.csc436.team_bubble_sort.lunchroll.entities.Group;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface UpdateGroup {
    void updateGroupRequest(Group group);
    void updateGroupSuccess(Group group);
    void updateGroupError(String error);
}
