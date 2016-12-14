package com.mypooja.androidapp.Common.Model;

/**
 * Created by Vinay on 21-06-2015.
 */
public class TokenResponse {
    String token_type;
    String access_token;
    int expires_in;

    public String GetTokenType () { return token_type; }
    public String GetAccessToken () { return access_token; }
    public int GetExpiresIn () { return expires_in; }
}
