package com.lizardpoint.lizardpointtrivia;

/**
 * Created by Geoff on 2018-03-30.
 */

//This is required so that an activity can collect the result of BackgroundTask after it finishes what it's doing
//said activity must implement this interface for this to work
public interface AsyncResponse {
    void processFinish(Quiz quiz);
}
