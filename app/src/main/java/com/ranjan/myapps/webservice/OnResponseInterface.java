package com.ranjan.myapps.webservice;

public interface OnResponseInterface {
    void onApiResponse(Object response);
    void onApiFailure(String message);
}
