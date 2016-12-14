package com.mypooja.androidapp.Common.Model;

/**
 * Created by Admin on 02-05-2015.
 */
public class AuthenticationData {
    String mAuthToken = null;
    public void SetAuthenticationToken (String token) {
        mAuthToken = token;
    }

    public String GetAuthenticationToken () {
        return mAuthToken;
    }

    private static AuthenticationData instance = null;
    public static AuthenticationData GetInstance () {
        if (instance == null) {
            instance = new AuthenticationData();
        }
        return instance;
    }
}
