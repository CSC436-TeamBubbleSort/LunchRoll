package com.csc436.team_bubble_sort.lunchroll.web_services;

import android.content.Context;
import android.graphics.AvoidXfermode;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.csc436.team_bubble_sort.lunchroll.entities.Group;
import com.csc436.team_bubble_sort.lunchroll.entities.ModelFactory;
import com.csc436.team_bubble_sort.lunchroll.web_services.group.DeleteGroup;
import com.csc436.team_bubble_sort.lunchroll.web_services.group.GetGroup;
import com.csc436.team_bubble_sort.lunchroll.web_services.group.GetGroups;
import com.csc436.team_bubble_sort.lunchroll.web_services.group.UpdateGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jonathan on 11/5/2015.
 */
public class GroupService extends BaseService{
    private Context context;
    private String baseRoute = "/group/";

    public GroupService(Context context){
        super();
        this.context = context;
    }

    public void getGroups(final GetGroups userActivity, int userId){
        Response.Listener responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("data");
                    userActivity.getGroupsSuccess(ModelFactory.CreateGroupListItem(array));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };
        Response.ErrorListener errorListener = new  Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                userActivity.getGroupsError("error" + error.getLocalizedMessage() + "\n" + error.getMessage());
            }
        };
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonBody != null){
            request.call( "/user/getGroups", jsonBody, responseListener, errorListener, context);
        }
        else{
            userActivity.getGroupsError("JSON could not be created.");
        }
    }

    public void GetGroup(final GetGroup userActivity, int groupId){
        Response.Listener responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("data");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                userActivity.getGroupSuccess(ModelFactory.CreateGroup(response));
            }
        };
        Response.ErrorListener errorListener = new  Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                userActivity.getGroupError("error" + error.getLocalizedMessage() + "\n" + error.getMessage());
            }
        };
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("groupId", groupId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonBody != null){

            request.call(baseRoute + "getGroup", jsonBody, responseListener, errorListener, context);
        }
        else{

        }
    }

    public void UpdateGroup(final UpdateGroup userActivity, final Group group){
        Response.Listener responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int groupId = 0;
                try {
                    response = response.getJSONObject("data");
                    groupId = response.getInt("groupId");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                group.setGroupId(groupId);
                userActivity.updateGroupSuccess(group);
            }
        };
        Response.ErrorListener errorListener = new  Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                userActivity.updateGroupError("error" + error.getLocalizedMessage() + "\n" + error.getMessage());
            }
        };
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("userId", group.getUserId());
            jsonBody.accumulate("groupId", group.getGroupId());
            jsonBody.accumulate("name", group.getName());
            jsonBody.accumulate("users", group.getUsers());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonBody != null){

            request.call(baseRoute + "updateGroup", jsonBody, responseListener, errorListener, context);
        }
        else{
            userActivity.updateGroupError("JSON could not be created.");
        }
    }

    public void DeleteGroup(final DeleteGroup userActivity, int groupId){
        Response.Listener responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("data");
                    if (response.getBoolean("success")){
                        userActivity.deleteGroupSuccess("Delete Succeeded");
                    }
                    else{
                        userActivity.deleteGroupSuccess("Delete Failed");
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new  Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                userActivity.deleteGroupError("error" + error.getLocalizedMessage() + "\n" + error.getMessage());
            }
        };
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("groupId", groupId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonBody != null){
            userActivity.deleteGroupError(jsonBody.toString());
            request.call(baseRoute + "deleteGroup", jsonBody, responseListener, errorListener, context);
        }
        else{
            userActivity.deleteGroupError("JSON could not be created.");
        }
    }
}
