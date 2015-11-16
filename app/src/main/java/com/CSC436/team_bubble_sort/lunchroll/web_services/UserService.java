package com.csc436.team_bubble_sort.lunchroll.web_services;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.csc436.team_bubble_sort.lunchroll.entities.User;
import com.csc436.team_bubble_sort.lunchroll.web_services.admin.GetUsers;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.UpdateUser;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.Login;

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

    public void updateUser(final UpdateUser userActivity, User user){
        Response.Listener responseLisenter = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("data");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                userActivity.updateUserSuccess(response.toString());
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
            request.call(baseRoute + "updateUser", jsonBody, responseLisenter, errorListener, context);
        }
        else{
            userActivity.updateUserError("JSON could not be created.");
        }
    }

    public void login(final Login userActivity, String password, String email){
        Response.Listener responseLisenter = new Response.Listener<JSONObject>() {
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
            request.call(baseRoute + "login", jsonBody, responseLisenter, errorListener, context);
        }
        else{
            userActivity.loginError("JSON could not be created.");
        }
    }

    public void getUsers(final GetUsers userActivity){
        Response.Listener responseLisenter = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("data");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                userActivity.getUsersSuccess(response.toString());
            }
        };
        Response.ErrorListener errorListener = new  Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                userActivity.getUsersError("error" + error.getLocalizedMessage() + "\n" + error.getMessage());
            }
        };
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("userId", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonBody != null){
            userActivity.getUsersError(jsonBody.toString());
            request.call("/admin/getUsers", jsonBody, responseLisenter, errorListener, context);
        }
        else{
            userActivity.getUsersError("JSON could not be created.");
        }
    }








}
