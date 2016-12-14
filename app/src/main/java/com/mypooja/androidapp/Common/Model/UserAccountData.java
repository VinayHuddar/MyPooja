package com.mypooja.androidapp.Common.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mypooja.androidapp.Common.Model.AuthenticationData;
import com.mypooja.androidapp.Common.Model.AccountData;
import com.google.gson.Gson;

/**
 * Created by vinayhuddar on 27/06/15.
 */
public class UserAccountData {
    static UserAccountData instance = null;

    public static UserAccountData GetInstance () {
        if (instance == null)
            instance = new UserAccountData();
        return instance;
    }

    public void SetAccountData (AccountData accountData, Context ctxt) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxt);
        SharedPreferences.Editor editor = preferences.edit();
        if (accountData != null) {
            editor.putString("USER_ACCOUNT_DATA", new Gson().toJson(accountData));
            editor.putString("AUTHENTICATION_TOKEN", AuthenticationData.GetInstance().GetAuthenticationToken());
            editor.commit();
        } else {
            editor.putString("USER_ACCOUNT_DATA", null);
            editor.putString("AUTHENTICATION_TOKEN", null);
            editor.commit();
        }
    }

    public AccountData GetAccountData (Context ctxt) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxt);

        String sobj = preferences.getString("USER_ACCOUNT_DATA", "");
        if(sobj.equals(""))
            return null;
        else
            return new Gson().fromJson(sobj, AccountData.class);
    }

    public String GetAuthenticationToken (Context ctxt) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxt);

        String authToken = preferences.getString("AUTHENTICATION_TOKEN", "");
        if(authToken.equals(""))
            return null;
        else
            return authToken;
    }
}
