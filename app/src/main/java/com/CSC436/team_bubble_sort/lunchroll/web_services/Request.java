package com.csc436.team_bubble_sort.lunchroll.web_services;

import android.content.Context;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jon on 10/31/2015.
 */

public class Request {

    private String baseURL = "http://52.8.67.183";

    public Request(){
    }

    public void call(String url, JSONObject jsonBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, Context context){

        JsonObjectRequest jsonRequest = new JsonObjectRequest(baseURL + url, jsonBody, listener, errorListener);

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }


}


