package com.csc436.team_bubble_sort.lunchroll.web_services;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.csc436.team_bubble_sort.lunchroll.entities.Friend;
import com.csc436.team_bubble_sort.lunchroll.entities.ModelFactory;
import com.csc436.team_bubble_sort.lunchroll.entities.Preferences;
import com.csc436.team_bubble_sort.lunchroll.entities.User;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.AddFriend;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.GetFriends;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.GetPreferences;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.RemoveFriend;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.UpdateUser;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jon on 11/5/2015.
 */
public class UserService extends BaseService {

    private Context context;
    private String baseRoute = "/user/";

    public UserService(Context context){
        super();
        this.context = context;
    }

    public void updateUser(final UpdateUser userActivity,final User user){
        Response.Listener responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int userId = 0;
                try {
                    response = response.getJSONObject("data");
                    userId = response.getInt("groupId");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                user.setUserId(userId);
                userActivity.updateUserSuccess(user);
            }
        };
        Response.ErrorListener errorListener = new  Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                userActivity.updateUserError("error" + error.getLocalizedMessage() + "\n" + error.getMessage());
            }
        };
        JSONObject jsonBody = null;
        jsonBody = user.toJSON();

        if (jsonBody != null){
            userActivity.updateUserError(jsonBody.toString());
            request.call(baseRoute + "updateUser", jsonBody, responseListener, errorListener, context);
        }
        else{
            userActivity.updateUserError("JSON could not be created.");
        }
    }

    public void login(final Login userActivity, String password, String email){
        Response.Listener responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("data");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                userActivity.loginSuccess(response.toString());
            }
        };
        Response.ErrorListener errorListener = new  Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                userActivity.loginError("error" + error.getLocalizedMessage() + "\n" + error.getMessage());
            }
        };
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("password", password);
            jsonBody.accumulate("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonBody != null){
            userActivity.loginError(jsonBody.toString());
            request.call(baseRoute + "login", jsonBody, responseListener, errorListener, context);
        }
        else{
            userActivity.loginError("JSON could not be created.");
        }
    }

    public void AddFriend(final AddFriend userActivity, Friend friend){
        Response.Listener responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("data");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                userActivity.addFriendSuccess(response.toString());
            }
        };
        Response.ErrorListener errorListener = new  Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                userActivity.addFriendError("error" + error.getLocalizedMessage() + "\n" + error.getMessage());
            }
        };
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("userId", friend.getUserId());
            jsonBody.accumulate("friendId", friend.getFriendId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonBody != null){
            userActivity.addFriendError(jsonBody.toString());
            request.call(baseRoute + "addFriend", jsonBody, responseListener, errorListener, context);
        }
        else{
            userActivity.addFriendError("JSON could not be created.");
        }
    }

    public void AddFriend(final RemoveFriend userActivity, Friend friend){
        Response.Listener responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("data");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                userActivity.removeFriendSuccess(response.toString());
            }
        };
        Response.ErrorListener errorListener = new  Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                userActivity.removeFriendError("error" + error.getLocalizedMessage() + "\n" + error.getMessage());
            }
        };
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("userId", friend.getUserId());
            jsonBody.accumulate("friendId", friend.getFriendId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonBody != null){
            userActivity.removeFriendError(jsonBody.toString());
            request.call(baseRoute + "removeFriend", jsonBody, responseListener, errorListener, context);
        }
        else{
            userActivity.removeFriendError("JSON could not be created.");
        }
    }

    public void GetFriends(final GetFriends userActivity, int userId){
        Response.Listener responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray array = response.getJSONArray("data");
                    userActivity.getFriendsSuccess(ModelFactory.CreateFriendListItem(array));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new  Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                userActivity.getFriendsError("error" + error.getLocalizedMessage() + "\n" + error.getMessage());
            }
        };
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonBody != null){
            userActivity.getFriendsError(jsonBody.toString());
            request.call(baseRoute + "getFriends", jsonBody, responseListener, errorListener, context);
        }
        else{
            userActivity.getFriendsError("JSON could not be created.");
        }
    }

    public void GetPreferences(final GetPreferences userActivity, final int userId){
        Response.Listener responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("data");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                userActivity.getPreferencesSuccess(new Preferences(userId, response));
            }
        };
        Response.ErrorListener errorListener = new  Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                userActivity.getPreferencesError("error" + error.getLocalizedMessage() + "\n" + error.getMessage());
            }
        };
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonBody != null){
            userActivity.getPreferencesError(jsonBody.toString());
            request.call(baseRoute + "getPreferences", jsonBody, responseListener, errorListener, context);
        }
        else{
            userActivity.getPreferencesError("JSON could not be created.");
        }
    }

}
