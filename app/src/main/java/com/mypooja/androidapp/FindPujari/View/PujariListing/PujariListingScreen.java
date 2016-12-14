package com.mypooja.androidapp.FindPujari.View.PujariListing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.mypooja.androidapp.FindPujari.Model.PujariAttributes;
import com.mypooja.androidapp.FindPujari.Model.PujariList;
import com.mypooja.androidapp.R;
import com.mypooja.androidapp.Common.View.BaseActivity;
import com.mypooja.androidapp.Common.View.NavDrawerItemList;
import com.mypooja.androidapp.Common.View.NavigationDrawerCallbacks;

import java.util.HashMap;
import java.util.Map;

public class PujariListingScreen extends BaseActivity implements NavigationDrawerCallbacks,
        PujariFilterDrawerFragment.Callbacks, PujariList.Callback, PujariAttributes.Callback {

    // Used to store the last screen title. For use in {@link #restoreActionBar()}.
    private CharSequence mTitle;

    boolean mDetailViewInitiated = false;
    Activity mParentActivity;
    PujariFilterDrawerFragment mPujariFilterDrawerFragment = null;

    PujariAttributes.Model mFilterOptions = null;
    PujariList mPujariList = null;
    PujariListAdapter mPujariListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pujari_listing);

        super.onCreateDrawer();

        mParentActivity = this;

        findViewById(R.id.loading_message).setVisibility(View.VISIBLE);
        PujariAttributes pujariAttributes = new PujariAttributes(this);
        pujariAttributes.FetchFilters();

        mPujariList = new PujariList (this);
        Map<String, String> queries = new HashMap<>();
        queries.put("page", "1");
        mPujariList.FetchPujariList(queries);
    }

    public void onFilterSelectionChanged (PujariFilterDrawerFragment.FilterSelections filters) {
        Map<String, String> queries = new HashMap<>();
        String key = null;
        String value = null;

        int numSelections = filters.poojaSelections.size();
        if (numSelections > 0) {
            key = "pooja";
            value = String.format("{%s", filters.poojaSelections.get(0));
            for (int i = 1; i < numSelections; i++)
                value = value.concat(String.format(", %s", filters.poojaSelections.get(i).toString()));
            value = value.concat("}");
            queries.put(key, value);
        }

        if (filters.casteSelection != null) {
            queries.put("caste", filters.casteSelection);
        }

        numSelections = filters.areaSelections.size();
        if (numSelections > 0) {
            key = "area";
            value = String.format("{%s", filters.areaSelections.get(0));
            for (int i = 1; i < numSelections; i++)
                value = value.concat(String.format(", %s", filters.areaSelections.get(i).toString()));
            value = value.concat("}");
            queries.put(key, value);
        }

        numSelections = filters.languageSelections.size();
        if (numSelections > 0) {
            key = "language";
            value = String.format("{%s", filters.languageSelections.get(0));
            for (int i = 1; i < numSelections; i++)
                value = value.concat(String.format(", %s", filters.languageSelections.get(i).toString()));
            value = value.concat("}");
            queries.put(key, value);
        }

        if (filters.dateSelection != null) {
            queries.put("date", filters.dateSelection);
        }

        if (filters.timeSelection != null) {
            queries.put("time", filters.timeSelection);
        }

        findViewById(R.id.loading_message).setVisibility(View.VISIBLE);
        mPujariList.FetchPujariList(queries);
    }

    PujariList.Model mListData = null;
    public void OnListReceived(PujariList.Model listData) {
        mListData = listData;
        if ((mPujariListAdapter == null) && mFilterOptions != null) {
            findViewById(R.id.loading_message).setVisibility(View.INVISIBLE);

            // Setup pujari ListView
            ListView pujariListView = (ListView)findViewById(R.id.PujarisListView);
            mPujariListAdapter = new PujariListAdapter(this, R.layout.layout_pujari_list_item, mListData, mFilterOptions);
            pujariListView.setAdapter(mPujariListAdapter);
        } else {
            mPujariListAdapter.ReInitializeList(mListData);
        }
    }

    public void OnPoojariSelectionFiltersReceived(PujariAttributes.Model filterOptions) {
        mFilterOptions = filterOptions;

        mPujariFilterDrawerFragment = (PujariFilterDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_filter_drawer);
        mPujariFilterDrawerFragment.setup(R.id.fragment_filter_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mFilterOptions);

        if ((mPujariListAdapter == null) && (mListData != null)) {
            findViewById(R.id.loading_message).setVisibility(View.INVISIBLE);

            // Setup pujari ListView
            ListView pujariListView = (ListView)findViewById(R.id.PujarisListView);
            mPujariListAdapter = new PujariListAdapter(this, R.layout.layout_pujari_list_item, mListData, mFilterOptions);
            pujariListView.setAdapter(mPujariListAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_pujari, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.PujariFilter) {
            mPujariFilterDrawerFragment.openDrawer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    }

    @Override
    public int getDefaultItemSelectId() {
        return NavDrawerItemList.MY_CART_ITEM_ID;
    }
}
