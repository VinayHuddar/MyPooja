package com.mypooja.androidapp.Common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mypooja.androidapp.Common.Model.AppSettingsData;
import com.mypooja.androidapp.Common.View.NavDrawerItemList;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Admin on 02-05-2015.
 */
public class ApplicationSettings {
    public static final String APPLICATION_SETTINGS_CRC = "application_settings_crc";
    public static final String ORDER_STATES = "order_states";
    public static final String PRODUCT_STATES = "product_states";

    private static ApplicationSettings instance = null;
    public static ApplicationSettings GetInstance () {
        if (instance == null) {
            instance = new ApplicationSettings();
        }
        return instance;
    }

    Callback mCallback;
    public void Init (Context appCtxt, Callback callback) {
        mApplContext = appCtxt;
        mCallback = callback;

        mPrefFile = PreferenceManager.getDefaultSharedPreferences(appCtxt);
    }

    Context mApplContext;
    SharedPreferences mPrefFile;
    public void SetApplSettings (AppSettingsData appSettingsData) {
        int storedCRC = mPrefFile.getInt(APPLICATION_SETTINGS_CRC, -1);
        // The following "if" construct must be enabed when the APIs are available
        //if ((storedCRC == -1) || (storedCRC != appSettingsData.GetCRC())) {
            SharedPreferences.Editor editor = mPrefFile.edit();

            editor.putInt(APPLICATION_SETTINGS_CRC, appSettingsData.GetCRC());

            WriteStringSet(editor, ORDER_STATES, appSettingsData.GetOrderStates());
            WriteStringSet(editor, PRODUCT_STATES, appSettingsData.GetProductStates());

            editor.commit();
        //}
    }

    private void WriteStringSet (SharedPreferences.Editor editor, String key, String[] strArray) {
        Set<String> strSet = new HashSet<>();
        for (int i = 0; i < strArray.length; i++)
            strSet.add(strArray[i]);
        editor.putStringSet(key, strSet);
    }

    public String[] GetOrderStates () {
        return ReadStringSet(ORDER_STATES);
    }

    public String[] GetProductStates () {
        return ReadStringSet(PRODUCT_STATES);
    }

    public String[] ReadStringSet (String key) {
        Set<String> strSet = mPrefFile.getStringSet(key, null);

        String[] strArray = null;
        if (strSet != null) {
            strArray = new String[strSet.size()];
            strSet.toArray(strArray);
        }
        return (strArray);
    }

    private int fetchAppSettingsRetryCnt = 0;
    public void FetchAppSettings () {
        /*mAPIService.GetAppSettings(new Callback<AppSettingsData>() {
            @Override
            public void success(AppSettingsData appSettingsData, Response response) {
                ApplicationSettings.GetInstance().Init(mApplCtxt);
                ApplicationSettings.GetInstance().SetApplSettings(appSettingsData);
                NavDrawerItemList.GetInstance().PrepareNavDrawerLists(getApplicationContext());

                if (CommonDefinitions.ENABLE_INTEGRATED_SERVICES_PAGE) {
                    Intent intent = new Intent(getApplicationContext(), IntegratedServicesScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                fetchAppSettingsRetryCnt++;
                if (fetchAppSettingsRetryCnt < CommonDefinitions.RETRY_COUNT)
                    FetchAuthKeyAndInitApp();
            }
        });*/

        String[] orderStates = new String[4];
        orderStates[0] = "Pending";
        orderStates[1] = "Shipped";
        orderStates[2] = "Completed";
        orderStates[3] = "Canceled";

        String[] productStates = new String[2];
        productStates[0] = "Enabled";
        productStates[1] = "Disabled";

        AppSettingsData appSettingsData = new AppSettingsData();
        appSettingsData.SetCRC(1234);
        appSettingsData.SetAppSettings(orderStates, productStates);

        SetApplSettings(appSettingsData);

        mCallback.OnApplicationSettingsInitialized();
    }



    public interface Callback {
        public void OnApplicationSettingsInitialized ();
    }

}
