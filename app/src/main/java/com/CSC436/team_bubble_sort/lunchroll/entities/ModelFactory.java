package com.csc436.team_bubble_sort.lunchroll.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 11/27/2015.
 */
public class ModelFactory {
    public static List<GroupListItem> CreateGroupListItem(JSONArray array){
        List<GroupListItem> groupListItems = new ArrayList<GroupListItem>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                groupListItems.add(new GroupListItem(obj.getInt("groupId"), obj.getString("name")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return groupListItems;
    }

    public static Group CreateGroup(JSONObject obj){
        int userId = 0;
        String name = "";
        String users = "";
        int groupId = 0;
        try {
            userId = obj.optInt("userId");
            name = obj.getString("name");
            users = obj.getString("users");
            groupId = obj.getInt("groupId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Group(userId, name, users, groupId);
    }
}
