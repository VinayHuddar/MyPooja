package com.mypooja.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mypooja.androidapp.Common.View.BaseActivity;
import com.mypooja.androidapp.Common.View.NavDrawerItemList;
import com.mypooja.androidapp.Common.View.NavigationDrawerCallbacks;
import com.mypooja.androidapp.FindPujari.View.PujariListing.PujariListingScreen;


public class MainScreen extends BaseActivity implements NavigationDrawerCallbacks {
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onCreateDrawer();
        mNavigationDrawerFragment.SetDefaultSelectedPosition(NavDrawerItemList.BROWSE_ITEM_ID);

        getSupportActionBar().setTitle("My Pooja");

        mActivity = this;

        View findPujari = findViewById(R.id.FindPujari);
        findPujari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, PujariListingScreen.class);
                startActivity(intent);
            }
        });

        View buySaamagri = findViewById(R.id.BuySaamagri);
        buySaamagri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Buy Pooja Saamagri Online...Coming soon", Toast.LENGTH_SHORT);
            }
        });

        View bookPooja = findViewById(R.id.BookPooja);
        bookPooja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Book Pooja in Temples...Coming soon", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    }

    @Override
    public int getDefaultItemSelectId() {
        return NavDrawerItemList.BROWSE_ITEM_ID;
    }
}
