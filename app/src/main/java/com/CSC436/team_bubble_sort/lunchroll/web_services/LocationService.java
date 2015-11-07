package com.csc436.team_bubble_sort.lunchroll.web_services;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.csc436.team_bubble_sort.lunchroll.web_services.location.NearbyAny;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.UpdateUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jon on 11/5/2015.
 */
public class LocationService extends BaseService {
    private Context context;
    private String baseRoute = "client/";

    public LocationService(Context context){
        super();
        this.context = context;
    }

    public void nearbyAny(final NearbyAny activity, double latitude, double longitude){
        Response.Listener responseLisenter = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("data");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                activity.nearbyAnySuccess(response.toString());
            }
        };
        Response.ErrorListener errorListener = new  Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                activity.nearbyAnyError("error" + error.getLocalizedMessage() + "\n" + error.getMessage());
            }
        };
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("latitude", "32.1958");
            jsonBody.accumulate("longitude", "-110.892");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonBody != null){
            activity.nearbyAnyError(jsonBody.toString());
            request.call(baseRoute + "nearbyAny", jsonBody, responseLisenter, errorListener, context);
        }
        else{
            activity.nearbyAnyError("JSON could not be created.");
        }
    }

}
