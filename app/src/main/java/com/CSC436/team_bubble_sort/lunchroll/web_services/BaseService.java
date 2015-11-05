package com.csc436.team_bubble_sort.lunchroll.web_services;

/**
 * Created by jon on 11/5/2015.
 */
public abstract class BaseService {

    protected Request request;

    public BaseService(){
        request = new Request();
    }
}
