package com.csc436.team_bubble_sort.lunchroll.web_services;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.csc436.team_bubble_sort.lunchroll.web_services.group.GetGroups;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jonathan on 11/5/2015.
 */
public class GroupService extends BaseService{
    private Context context;
    private String baseRoute = "/user/";

    public GroupService(Context context){
        super();
        this.context = context;
    }

    public void getGroups(final GetGroups userActivity, String userId){
        Response.Listener responseLisenter = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("data");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                userActivity.getGroupsSuccess(response.toString());
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
            userActivity.getGroupsError(jsonBody.toString());
            request.call(baseRoute + "getGroups", jsonBody, responseLisenter, errorListener, context);
        }
        else{
            userActivity.getGroupsError("JSON could not be created.");
        }
    }
}
