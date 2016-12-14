package com.mypooja.androidapp.Common.Model;

/**
 * Created by vinayhuddar on 26/06/15.
 */
public class AccountData {
    Account account;

    class Account {
        int customer_id;
        int seller_id;
        String firstname;
        String lastname;
        String email;
        String mobile;
        boolean newsletter;
        int reward_points;
        String balance;
        CustomerGroup customer_group;
        Defaults defaults;
    }

    class CustomerGroup {
        int customer_group_id;
        String name;
        String description;
    }

    class Defaults {
        int delivery_address_id;
        String payment_type;
        String time_slot;
    }

    public int GetDefaultDeliveryAddressId () { return account.defaults.delivery_address_id; }
    public String GetMobileNumber () { return account.mobile; }
    public String GetEmail () { return account.email; }
    public String GetFirstName () { return account.firstname; }

    public int GetSellerId () { return account.seller_id; }

}
