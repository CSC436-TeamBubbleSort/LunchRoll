package com.csc436.team_bubble_sort.lunchroll.web_services.group;

/**
 * Created by Jonathan on 11/27/2015.
 */
public interface DeleteGroup {
    void deleteGroupRequest(int groupId);
    void deleteGroupSuccess(String response);
    void deleteGroupError(String error);
}
