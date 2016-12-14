package com.mypooja.androidapp.Common.View;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mypooja.androidapp.Common.CommonDefinitions;
import com.mypooja.androidapp.MyPoojaApplication;
import com.mypooja.androidapp.Common.Model.AccountData;
import com.mypooja.androidapp.Common.Model.UserAccountData;
import com.mypooja.androidapp.R;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by poliveira on 24/10/2014.
 */
public class NavigationDrawerFragment extends Fragment implements NavigationDrawerCallbacks {
    public static final String FROM_NAV_DRAWER = "from_nav_drawer";

    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREFERENCES_FILE = "my_app_settings"; //TODO: change this to your file
    private NavigationDrawerCallbacks mCallbacks;
    private RecyclerView mDrawerList;
    private View mFragmentContainerView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private int mCurrentSelectedPosition;

    View mNavDrawerView;
    NavigationDrawerAdapter mNavDrawerAdapter_SignedIn;
    NavigationDrawerAdapter mNavDrawerAdapter_NotSignedIn;
    ViewGroup mContainer;

    private static boolean NOT_SIGNED_IN = false;
    private static boolean SIGNED_IN = true;
    boolean mCurrentSignInStatus = NOT_SIGNED_IN;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContainer = container;
        mNavDrawerView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mDrawerList = (RecyclerView) mNavDrawerView.findViewById(R.id.drawerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setHasFixedSize(true);

        InitializeNavDrawerList();

        //selectItem(mCurrentSelectedPosition);

        Drawable signinIcon = getResources().getDrawable(R.drawable.sign_in_white);
        signinIcon.setBounds(0, 0, 32, 32);
        TextView signInItem = (TextView) mNavDrawerView.findViewById(R.id.signin);
        signInItem.setCompoundDrawables(signinIcon, null, null, null);

        return mNavDrawerView;
    }

    int mDefaultSelectedItemId = -1;
    public void SetDefaultSelectedPosition (int defaultSelectedItemId) {
        mDefaultSelectedItemId = defaultSelectedItemId;

        AccountData userAccountData = UserAccountData.GetInstance().GetAccountData(getActivity());
        if (userAccountData == null) {
            int posNotSignedIn = NavDrawerItemList.GetInstance().GetPositionInList(mDefaultSelectedItemId, false);
            mNavDrawerAdapter_NotSignedIn.selectPosition(posNotSignedIn);
        } else {
            int posSignedIn = NavDrawerItemList.GetInstance().GetPositionInList(mDefaultSelectedItemId, true);
            mNavDrawerAdapter_SignedIn.selectPosition(posSignedIn);
        }
    }

    void InitializeNavDrawerList() {
        int posNotSignedIn = NavDrawerItemList.GetInstance().GetPositionInList(mDefaultSelectedItemId, false);
        mNavDrawerAdapter_NotSignedIn = new NavigationDrawerAdapter(NavDrawerItemList.GetInstance().GetNotSignedInList(), posNotSignedIn);
        mNavDrawerAdapter_NotSignedIn.setNavigationDrawerCallbacks(this);

        int posSignedIn = NavDrawerItemList.GetInstance().GetPositionInList(mDefaultSelectedItemId, true);
        mNavDrawerAdapter_SignedIn = new NavigationDrawerAdapter(NavDrawerItemList.GetInstance().GetSignedInList(), posSignedIn);
        mNavDrawerAdapter_SignedIn.setNavigationDrawerCallbacks(this);

        final TextView signInItem = (TextView) mNavDrawerView.findViewById(R.id.signin);
        AccountData userAccountData = UserAccountData.GetInstance().GetAccountData(getActivity());
        if (userAccountData == null) {
            signInItem.setText(getResources().getText(R.string.sign_in));

            mDrawerList.setAdapter(mNavDrawerAdapter_NotSignedIn);
            mCurrentSignInStatus = NOT_SIGNED_IN;
        }
        else {
            signInItem.setText(String.format("Hi %s", userAccountData.GetFirstName()));

            mDrawerList.setAdapter(mNavDrawerAdapter_SignedIn);
            mCurrentSignInStatus = SIGNED_IN;
        }

        signInItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentSignInStatus == NOT_SIGNED_IN) {
                    /*Intent intent = new Intent(getActivity(), SignInActivity.class);
                    intent.putExtra(FROM_NAV_DRAWER, true);
                    startActivity(intent);*/
                }
            }
        });

        final ImageView signOutItem = (ImageView) mNavDrawerView.findViewById(R.id.signout);
        if (mCurrentSignInStatus == NOT_SIGNED_IN)
            signOutItem.setVisibility(View.INVISIBLE);
        else
            signOutItem.setVisibility(View.VISIBLE);

        signOutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the popup_layout.xml
                LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.navdrawer_signout_popup, mContainer);

                // Creating the PopupWindow
                final PopupWindow popup = new PopupWindow(getActivity());
                popup.setContentView(layout);
                popup.setWidth(140);
                popup.setHeight(70);
                popup.setFocusable(true);

                // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
                int OFFSET_X = -50;
                int OFFSET_Y = 80;

                int[] location = new int[2];
                signOutItem.getLocationOnScreen(location);

                // Displaying the popup at the specified location, + offsets.
                popup.showAtLocation(layout, Gravity.NO_GRAVITY, location[0] + OFFSET_X, location[1] + OFFSET_Y);

                ((TextView)layout.findViewById(R.id.signout_popup)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SignOut();
                        popup.dismiss();
                    }
                });
            }
        });
    }

    int mLogoutRetryCnt = 0;
    void SignOut() {
        MyPoojaApplication.GetAPIService().GetLogout(new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Toast.makeText(getActivity(), "Sign out Successful", Toast.LENGTH_SHORT).show();
                UserAccountData.GetInstance().SetAccountData(null, getActivity());

                UpdateList();
            }

            @Override
            public void failure(RetrofitError error) {
                if (mLogoutRetryCnt < CommonDefinitions.RETRY_COUNT) {
                    mLogoutRetryCnt++;
                    SignOut();
                } else {
                    mLogoutRetryCnt = 0;
                }

            }
        });
    }

    void UpdateList() {
        AccountData userAccountData = UserAccountData.GetInstance().GetAccountData(getActivity());
        if ((mCurrentSignInStatus == SIGNED_IN) && (userAccountData == null)) {
            TextView signInItem = (TextView) mNavDrawerView.findViewById(R.id.signin);
            signInItem.setText(getResources().getText(R.string.sign_in));

            ImageView signOutItem = (ImageView) mNavDrawerView.findViewById(R.id.signout);
            signOutItem.setVisibility(View.INVISIBLE);

            mDrawerList.setAdapter(mNavDrawerAdapter_NotSignedIn);
            mCurrentSignInStatus = NOT_SIGNED_IN;
        } else if ((mCurrentSignInStatus == NOT_SIGNED_IN) && (userAccountData != null)) {
            TextView signInItem = (TextView) mNavDrawerView.findViewById(R.id.signin);
            signInItem.setText(String.format("Hi %s", userAccountData.GetFirstName()));

            ImageView signOutItem = (ImageView) mNavDrawerView.findViewById(R.id.signout);
            signOutItem.setVisibility(View.VISIBLE);

            //mNavDrawerItemList = NavDrawerItemList.GetInstance().GetSignedInList();
            //mNavDrawerAdapter.notifyDataSetChanged();
            mDrawerList.setAdapter(mNavDrawerAdapter_SignedIn);
            mCurrentSignInStatus = SIGNED_IN;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        UpdateList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(getActivity(), PREF_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
            mDefaultSelectedItemId = mCallbacks.getDefaultItemSelectId();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return mActionBarDrawerToggle;
    }

    public void setActionBarDrawerToggle(ActionBarDrawerToggle actionBarDrawerToggle) {
        mActionBarDrawerToggle = actionBarDrawerToggle;
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        if(mFragmentContainerView.getParent() instanceof ScrimInsetsFrameLayout){
            mFragmentContainerView = (View) mFragmentContainerView.getParent();
        }
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.primary_color_dark));

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) return;
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveSharedSetting(getActivity(), PREF_USER_LEARNED_DRAWER, "true");
                }

                getActivity().invalidateOptionsMenu();
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState)
            mDrawerLayout.openDrawer(mFragmentContainerView);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    /**
     * Changes the icon of the drawer to back
     */
    public void showBackButton() {
        if (getActivity() instanceof ActionBarActivity) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Changes the icon of the drawer to menu
     */
    public void showDrawerButton() {
        if (getActivity() instanceof ActionBarActivity) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        mActionBarDrawerToggle.syncState();
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    @Override
    public void onNavigationDrawerItemSelected(int itemId) {
        mCurrentSelectedPosition = itemId;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }

        //((NavigationDrawerAdapter) mDrawerList.getAdapter()).selectPosition(itemId);

        /*if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }*/
        Intent intent;

        switch (itemId) {
            case NavDrawerItemList.MY_SELLER_ACCOUNT_ITEM_ID:
                //intent = new Intent(getActivity(), SellerAccountActivity.class);
                //startActivity(intent);
                break;
            case NavDrawerItemList.MY_ACCOUNT_ITEM_ID:
                //intent = new Intent(getActivity(), MyAccountActivity.class);
                //startActivity(intent);
                break;
            case NavDrawerItemList.HOME_ITEM_ID: // Go to Main Categories
            case NavDrawerItemList.BROWSE_ITEM_ID: // Go to Main Categories
                /*intent = new Intent(getActivity(), CategoryActivity.class);
                int[] lineage = new int[1]; lineage[0] = -1;
                intent.putExtra(CategoryActivity.LINEAGE, lineage);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
                break;
            case NavDrawerItemList.SEARCH_ITEM_ID:
                break;
            case NavDrawerItemList.MY_CART_ITEM_ID:
                //startActivity(new Intent(getActivity(), CartActivity.class));
                break;
            case NavDrawerItemList.TRACK_MY_ORDER_ITEM_ID: break;
            case NavDrawerItemList.OFFERS_ITEM_ID: break;
            case NavDrawerItemList.REFER_FRIENDS_ITEM_ID: break;
            case NavDrawerItemList.CALL_US_ITEM_ID: break;
            case NavDrawerItemList.HELP_ITEM_ID: break;
            case NavDrawerItemList.PREFERENCES_ITEM_ID: break;
        }
    }

    @Override
    public int getDefaultItemSelectId() {
        return -1;
    }


}
