package com.mypooja.androidapp.Common;

import com.mypooja.androidapp.Common.Model.AccountData;
import com.mypooja.androidapp.Common.Model.AppSettingsData;
import com.mypooja.androidapp.Common.Model.TokenResponse;
import com.mypooja.androidapp.FindPujari.Model.PujariAttributes;
import com.mypooja.androidapp.FindPujari.Model.PujariList;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by Vinay on 08-06-2015.
 */
public interface APIService {
    @POST("/oauth2/token")
    void GetAuthenticationToken(@Header("Accept") String accept, @Header("Authorization") String authHdr, @Query("grant_type") String grantType, @Body String emptyString,
                                Callback<TokenResponse> callback);

    @GET("/common/settings")
    void GetAppSettings(Callback<AppSettingsData> callback);

    /****************** Account APIs ******************/

    @FormUrlEncoded
    @POST("/account/login")
    void PostLogin (
            @Field("email") String email,
            @Field("password") String password,
            Callback<AccountData> callback);

    @GET("/account/logout")
    void GetLogout (Callback<String> callback);

    /******************* Poojari Listing related APIs *****************/

    @GET("/poojari/filters")
    void GetPoojariSelectionFilters(Callback<PujariAttributes.Model> callback);

    @GET("/poojari/city/{id}")
    void GetPoojariList(@QueryMap Map<String, String> filters, Callback<PujariList.Model> callback);

}
