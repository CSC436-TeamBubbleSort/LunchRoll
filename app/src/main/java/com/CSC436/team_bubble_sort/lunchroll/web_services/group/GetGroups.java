package com.csc436.team_bubble_sort.lunchroll.web_services.group;

/**
 * Created by jon on 11/5/2015.
 */
public interface GetGroups {
    public void getGroupsRequest();
    public void getGroupsSuccess(String result);
    public void getGroupsError(String error);
}
