package com.mypooja.androidapp.Common.View;

import android.content.Context;

import com.mypooja.androidapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinayhuddar on 13/07/15.
 */
public class NavDrawerItemList {
    private static NavDrawerItemList singularInstance = null;
    public static NavDrawerItemList GetInstance () {
        if (singularInstance == null)
            singularInstance = new NavDrawerItemList();

        return singularInstance;
    }

    public static final int SIGNIN_BENEFITS_NOTE_ID = 0;
    public static final int MY_SELLER_ACCOUNT_ITEM_ID = 1;
    public static final int MY_ACCOUNT_ITEM_ID = 2;
    public static final int MY_WISHLIST_ITEM_ID = 3;
    public static final int HOME_ITEM_ID = 4;
    public static final int BROWSE_ITEM_ID = 5;
    public static final int SEARCH_ITEM_ID = 6;
    public static final int MY_CART_ITEM_ID = 7;
    public static final int TRACK_MY_ORDER_ITEM_ID = 8;
    public static final int OFFERS_ITEM_ID = 9;
    public static final int REFER_FRIENDS_ITEM_ID = 10;
    public static final int CALL_US_ITEM_ID = 11;
    public static final int HELP_ITEM_ID = 12;
    public static final int PREFERENCES_ITEM_ID = 13;

    private static NavigationItem mSignInBenefitsnote = null;
    private static NavigationItem mMySellerAccount = null;
    private static NavigationItem mMyAccount = null;
    private static NavigationItem mMyWishList = null;
    private static NavigationItem mHome = null;
    private static NavigationItem mBrowse = null;
    private static NavigationItem mSearch = null;
    private static NavigationItem mMyCart = null;
    private static NavigationItem mTrackMyOrder = null;
    private static NavigationItem mOffers = null;
    private static NavigationItem mReferFriends = null;
    private static NavigationItem mCallUs = null;
    private static NavigationItem mHelp = null;
    private static NavigationItem mPreferences = null;

    private static List<NavigationItem> mSignedInList = new ArrayList<NavigationItem>();;
    private static List<NavigationItem> mNotSignedInList = new ArrayList<NavigationItem>();;

    public void PrepareNavDrawerLists (Context context) {
        mSignInBenefitsnote = new NavigationItem(SIGNIN_BENEFITS_NOTE_ID,
                context.getResources().getText(R.string.signin_benefits_note).toString(),
                context.getResources().getDrawable(R.drawable.signin_benefits_note),
                context.getResources().getDrawable(R.drawable.signin_benefits_note), false);
        mMySellerAccount = new NavigationItem(MY_SELLER_ACCOUNT_ITEM_ID,
                context.getResources().getText(R.string.temple_account).toString(),
                context.getResources().getDrawable(R.drawable.seller),
                context.getResources().getDrawable(R.drawable.seller_purple), false);
        mMyAccount = new NavigationItem(MY_ACCOUNT_ITEM_ID,
                context.getResources().getText(R.string.my_account).toString(),
                context.getResources().getDrawable(R.drawable.user_account),
                context.getResources().getDrawable(R.drawable.user_account_purple), false);
        mMyWishList = new NavigationItem(MY_WISHLIST_ITEM_ID,
                context.getResources().getText(R.string.my_wishlist).toString(),
                context.getResources().getDrawable(R.drawable.my_wishlist),
                context.getResources().getDrawable(R.drawable.my_wishlist_purple), false);
        mHome = new NavigationItem(HOME_ITEM_ID,
                context.getResources().getText(R.string.home).toString(),
                context.getResources().getDrawable(R.drawable.home),
                context.getResources().getDrawable(R.drawable.home_purple), true);
        mBrowse = new NavigationItem(BROWSE_ITEM_ID,
                context.getResources().getText(R.string.browse_and_pick).toString(),
                context.getResources().getDrawable(R.drawable.browse_and_add),
                context.getResources().getDrawable(R.drawable.browse_and_add_purple), false);
        mSearch = new NavigationItem(SEARCH_ITEM_ID,
                context.getResources().getText(R.string.search).toString(),
                context.getResources().getDrawable(R.drawable.search),
                context.getResources().getDrawable(R.drawable.search_purple), false);
        mMyCart = new NavigationItem(MY_CART_ITEM_ID,
                context.getResources().getText(R.string.my_cart).toString(),
                context.getResources().getDrawable(R.drawable.cart),
                context.getResources().getDrawable(R.drawable.cart_purple), false);
        mTrackMyOrder = new NavigationItem(TRACK_MY_ORDER_ITEM_ID,
                context.getResources().getText(R.string.track_order).toString(),
                context.getResources().getDrawable(R.drawable.track_order),
                context.getResources().getDrawable(R.drawable.track_order_purple), false);

        mOffers = new NavigationItem(OFFERS_ITEM_ID,
                context.getResources().getText(R.string.offers).toString(),
                context.getResources().getDrawable(R.drawable.offer),
                context.getResources().getDrawable(R.drawable.offer_purple), true);
        mReferFriends = new NavigationItem(REFER_FRIENDS_ITEM_ID,
                context.getResources().getText(R.string.refer_friends).toString(),
                context.getResources().getDrawable(R.drawable.refer_friends),
                context.getResources().getDrawable(R.drawable.refer_friends_purple), false);

        mCallUs = new NavigationItem(CALL_US_ITEM_ID,
                context.getResources().getText(R.string.call_us).toString(),
                context.getResources().getDrawable(R.drawable.call_us),
                context.getResources().getDrawable(R.drawable.call_us_purple), true);
        mHelp = new NavigationItem(HELP_ITEM_ID,
                context.getResources().getText(R.string.help).toString(),
                context.getResources().getDrawable(R.drawable.help),
                context.getResources().getDrawable(R.drawable.help_purple), false);
        mPreferences = new NavigationItem(PREFERENCES_ITEM_ID,
                context.getResources().getText(R.string.preferences).toString(),
                context.getResources().getDrawable(R.drawable.preferences),
                context.getResources().getDrawable(R.drawable.preferences_purple), false);

        // List to be shown when user is signed in
        mSignedInList.add(mMySellerAccount);
        mSignedInList.add(mMyAccount);
        mSignedInList.add(mMyWishList);
        mSignedInList.add(mHome);
        mSignedInList.add(mBrowse);
        mSignedInList.add(mSearch);
        mSignedInList.add(mMyCart);
        mSignedInList.add(mTrackMyOrder);
        mSignedInList.add(mOffers);
        mSignedInList.add(mReferFriends);
        mSignedInList.add(mCallUs);
        mSignedInList.add(mHelp);
        mSignedInList.add(mPreferences);

        // List to be shown when user is not signed in
        mNotSignedInList.add(mSignInBenefitsnote);
        mNotSignedInList.add(mHome);
        mNotSignedInList.add(mBrowse);
        mNotSignedInList.add(mSearch);
        mNotSignedInList.add(mMyCart);
        mNotSignedInList.add(mTrackMyOrder);
        mNotSignedInList.add(mOffers);
        mNotSignedInList.add(mReferFriends);
        mNotSignedInList.add(mCallUs);
        mNotSignedInList.add(mHelp);
        mNotSignedInList.add(mPreferences);
    }

    public List<NavigationItem> GetSignedInList () { return mSignedInList; }

    public List<NavigationItem> GetNotSignedInList () { return mNotSignedInList; }

    public int GetPositionInList (int itemId, boolean signedInList) {
        final int INVALID_POSITION = -1;
        switch (itemId) {
            case SIGNIN_BENEFITS_NOTE_ID:
                return signedInList ? INVALID_POSITION : mNotSignedInList.indexOf(mSignInBenefitsnote);
            case MY_SELLER_ACCOUNT_ITEM_ID:
                return signedInList ? mSignedInList.indexOf(mMySellerAccount) : INVALID_POSITION;
            case MY_ACCOUNT_ITEM_ID:
                return signedInList ? mSignedInList.indexOf(mMyAccount) : INVALID_POSITION;
            case MY_WISHLIST_ITEM_ID:
                return signedInList ? mSignedInList.indexOf(mMyWishList) : INVALID_POSITION;
            case HOME_ITEM_ID:
                return signedInList ? mSignedInList.indexOf(mHome) : mNotSignedInList.indexOf(mHome);
            case BROWSE_ITEM_ID:
                return signedInList ? mSignedInList.indexOf(mBrowse) : mNotSignedInList.indexOf(mBrowse);
            case SEARCH_ITEM_ID:
                return signedInList ? mSignedInList.indexOf(mSearch) : mNotSignedInList.indexOf(mSearch);
            case MY_CART_ITEM_ID:
                return signedInList ? mSignedInList.indexOf(mMyCart) : mNotSignedInList.indexOf(mMyCart);
            case TRACK_MY_ORDER_ITEM_ID:
                return signedInList ? mSignedInList.indexOf(mTrackMyOrder) : mNotSignedInList.indexOf(mTrackMyOrder);
            case OFFERS_ITEM_ID:
                return signedInList ? mSignedInList.indexOf(mOffers) : mNotSignedInList.indexOf(mOffers);
            case REFER_FRIENDS_ITEM_ID:
                return signedInList ? mSignedInList.indexOf(mReferFriends) : mNotSignedInList.indexOf(mReferFriends);
            case CALL_US_ITEM_ID:
                return signedInList ? mSignedInList.indexOf(mCallUs) : mNotSignedInList.indexOf(mCallUs);
            case HELP_ITEM_ID:
                return signedInList ? mSignedInList.indexOf(mHelp) : mNotSignedInList.indexOf(mHelp);
            case PREFERENCES_ITEM_ID:
                return signedInList ? mSignedInList.indexOf(mPreferences) : mNotSignedInList.indexOf(mPreferences);
            default:
                return INVALID_POSITION;
        }
    }
}
