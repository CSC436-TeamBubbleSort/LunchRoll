package com.csc436.team_bubble_sort.lunchroll.web_services;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.csc436.team_bubble_sort.lunchroll.model.AppUser;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.UpdateUser;

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

    public void updateUser(final UpdateUser userActivity, AppUser user){
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






}
