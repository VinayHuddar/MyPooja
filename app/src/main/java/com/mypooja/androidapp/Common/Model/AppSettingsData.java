package com.mypooja.androidapp.Common.Model;

/**
 * Created by vinayhuddar on 23/08/15.
 */
public class AppSettingsData {
    int crc;
    AppSettings app_settings;

    public class AppSettings {
        String[] order_states;
        String[] product_states;

        public AppSettings (String[] _order_states, String[] _product_states) {
            order_states = _order_states;
            product_states = _product_states;
        }
    }

    public int GetCRC () { return crc; }
    public AppSettings GetAppSettings () { return app_settings; }

    public void SetCRC (int _crc) { crc = _crc; }
    public void SetAppSettings (String[] orderStates, String[] productStates) {
        app_settings = new AppSettings(orderStates, productStates);
    }

    public String[] GetOrderStates () { return app_settings.order_states; }
    public String[] GetProductStates () { return app_settings.product_states; }
}
