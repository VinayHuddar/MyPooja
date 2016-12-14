package com.mypooja.androidapp.FindPujari.View.PujariListing;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mypooja.androidapp.R;
import com.mypooja.androidapp.Common.View.ScrimInsetsFrameLayout;
import com.mypooja.androidapp.Common.View.UnrollListView;
import com.mypooja.androidapp.FindPujari.Model.PujariAttributes;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by poliveira on 24/10/2014.
 */
public class PujariFilterDrawerFragment extends Fragment implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREFERENCES_FILE = "my_app_settings"; //TODO: change this to your file

    private Callbacks mCallback;
    private View mFragmentContainerView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle=null;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private int mCurrentSelectedPosition;

    PujariAttributes.Model mFilterOptions;
    PujariFilterDrawerFragment mFragment = null;

    public class FilterSelections {
        ArrayList<Integer> poojaSelections;
        String casteSelection;
        ArrayList<Integer> areaSelections;
        ArrayList<Integer> languageSelections;
        String dateSelection;
        String timeSelection;

        public FilterSelections () {
            poojaSelections = new ArrayList<>();
            casteSelection = null;
            areaSelections = new ArrayList<>();
            languageSelections = new ArrayList<>();
            dateSelection = null;
            timeSelection = null;
        }
    }

    FilterSelections mFilterSelections;
    View mRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_pujaris_filter_drawer, container, false);

        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(getActivity(), PREF_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        } else
            mCurrentSelectedPosition = 0;

        mFilterSelections = new FilterSelections();

        return mRoot;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragment = this;
    }

    Activity mActivity = null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        try {
            mCallback = (Callbacks)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement FilterDrawerCallbacks.");
        }
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, PujariAttributes.Model filterOptions) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        if(mFragmentContainerView.getParent() instanceof ScrimInsetsFrameLayout){
            mFragmentContainerView = (View) mFragmentContainerView.getParent();
        }
        mDrawerLayout = drawerLayout;

        mDrawerLayout.setDrawerListener((new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (!isAdded()) return;
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (!isAdded()) return;
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        }));

        mFilterOptions = filterOptions;
        LayoutViews();
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
        mCallback = null;
    }

    /*public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mActionBarDrawerToggle != null)
            mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    /*public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }*/

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

    public interface Callbacks {
        void onFilterSelectionChanged(FilterSelections filters);
    }

    ListView mPoojaList, mCasteList, mLanguageList, mAreaList, mDayList, mTimeList;
    CheckedTextView mPoojaFilterSelect, mCasteFilterSelect, mLanguageFilterSelect, mAreaFilterSelect, mDateFilterSelect, mTimeFilterSelect;
    public void LayoutViews () {
        mPoojaList = (ListView)mRoot.findViewById(R.id.SelectPoojaFilterList);
        mPoojaFilterSelect = (CheckedTextView)mRoot.findViewById(R.id.SelectPooja);
        mPoojaFilterSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPoojaFilterSelect.isChecked() == false) {
                    mPoojaFilterSelect.setChecked(true);
                    ShowFilter(mPoojaFilterSelect, mPoojaList);
                }
            }
        });

        mCasteList = (ListView)mRoot.findViewById(R.id.SelectCasteFilterList);
        mCasteFilterSelect = (CheckedTextView)mRoot.findViewById(R.id.SelectCaste);
        mCasteFilterSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCasteFilterSelect.isChecked() == false) {
                    mCasteFilterSelect.setChecked(true);
                    ShowFilter(mCasteFilterSelect, mCasteList);
                }
            }
        });

        mLanguageList = (ListView)mRoot.findViewById(R.id.SelectLanguageFilterList);
        mLanguageFilterSelect = (CheckedTextView)mRoot.findViewById(R.id.SelectLanguage);
        mLanguageFilterSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLanguageFilterSelect.isChecked() == false) {
                    mLanguageFilterSelect.setChecked(true);
                    ShowFilter(mLanguageFilterSelect, mLanguageList);
                }
            }
        });

        mAreaList = (ListView)mRoot.findViewById(R.id.SelectAreaFilterList);
        mAreaFilterSelect = (CheckedTextView)mRoot.findViewById(R.id.SelectArea);
        mAreaFilterSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAreaFilterSelect.isChecked() == false) {
                    mAreaFilterSelect.setChecked(true);
                    ShowFilter(mAreaFilterSelect, mAreaList);
                }
            }
        });

        mDateFilterSelect = (CheckedTextView)mRoot.findViewById(R.id.SelectPoojaDate);
        mDateFilterSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateFilterSelect.setChecked(true);
                DialogFragment newFragment = DatePickerFragment.newInstance(mFragment);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        mTimeFilterSelect = (CheckedTextView)mRoot.findViewById(R.id.SelectPoojaTime);
        mTimeFilterSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimeFilterSelect.isChecked() == false) {
                    mTimeFilterSelect.setChecked(true);
                    DialogFragment newFragment = TimePickerFragment.newInstance(mFragment);
                    newFragment.show(getFragmentManager(), "timePicker");
                }
            }
        });

        ArrayAdapter<String> poojaListAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_multi_choice_selector, mFilterOptions.GetPoojaNames());
        mPoojaList.setAdapter(poojaListAdapter);
        final ArrayAdapter<String> selectedPoojaFilterItems = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_tiny_and_black_font);
        final ListView selectedPoojaItemsList = (ListView) mRoot.findViewById(R.id.SelectedPoojaFilters);
        selectedPoojaItemsList.setAdapter(selectedPoojaFilterItems);
        mPoojaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView filterItem = (CheckedTextView) view;
                if (filterItem.isChecked()) {
                    mFilterSelections.poojaSelections.add(position);
                    selectedPoojaFilterItems.add(mFilterOptions.GetPoojaName(position));
                    filterItem.setChecked(true);
                } else {
                    mFilterSelections.poojaSelections.remove(new Integer(position));
                    selectedPoojaFilterItems.remove(mFilterOptions.GetPoojaName(position));
                    filterItem.setChecked(false);
                }
                selectedPoojaFilterItems.notifyDataSetChanged();
                UnrollListView.setListViewHeightBasedOnItems((ListView) selectedPoojaItemsList);

                if (mFilterSelections.poojaSelections.isEmpty()) {
                    selectedPoojaItemsList.setVisibility(View.GONE);
                } else {
                    selectedPoojaItemsList.setVisibility(View.VISIBLE);
                }
            }
        });

        ArrayAdapter<String> casteListAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_single_choice_selector, mFilterOptions.GetCommunityNames());
        mCasteList.setAdapter(casteListAdapter);
        final TextView selectedCaste = (TextView) mRoot.findViewById(R.id.SelectedCasteFilters);
        mCasteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFilterSelections.casteSelection = mFilterOptions.GetCasteName(position);
                selectedCaste.setText(mFilterSelections.casteSelection);
                selectedCaste.setVisibility(View.VISIBLE);
            }
        });

        ArrayAdapter<String> languageListAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_multi_choice_selector, mFilterOptions.GetLanguageNames());
        mLanguageList.setAdapter(languageListAdapter);
        final ArrayAdapter<String> selectedLanguageFilterItems = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_tiny_and_black_font);
        final ListView selectedLanguageItemsList = (ListView) mRoot.findViewById(R.id.SelectedLanguageFilters);
        selectedLanguageItemsList.setAdapter(selectedLanguageFilterItems);
        mLanguageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView filterItem = (CheckedTextView) view;
                if (filterItem.isChecked()) {
                    mFilterSelections.languageSelections.add(position);
                    selectedLanguageFilterItems.add(mFilterOptions.GetLanguageName(position));
                    filterItem.setChecked(true);
                } else {
                    mFilterSelections.languageSelections.remove(new Integer(position));
                    selectedLanguageFilterItems.remove(mFilterOptions.GetLanguageName(position));
                    filterItem.setChecked(false);
                }
                selectedLanguageFilterItems.notifyDataSetChanged();
                UnrollListView.setListViewHeightBasedOnItems((ListView) selectedLanguageItemsList);

                if (mFilterSelections.languageSelections.isEmpty()) {
                    selectedLanguageItemsList.setVisibility(View.GONE);
                } else {
                    selectedLanguageItemsList.setVisibility(View.VISIBLE);
                }
            }
        });


        ArrayAdapter<String> areaListAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_multi_choice_selector, mFilterOptions.GetAreaNames());
        mAreaList.setAdapter(areaListAdapter);
        final ArrayAdapter<String> selectedAreaFilterItems = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_tiny_and_black_font);
        final ListView selectedAreaItemsList = (ListView) mRoot.findViewById(R.id.SelectedAreaFilters);
        selectedAreaItemsList.setAdapter(selectedAreaFilterItems);
        mAreaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView filterItem = (CheckedTextView) view;
                if (filterItem.isChecked()) {
                    mFilterSelections.areaSelections.add(position);
                    selectedAreaFilterItems.add(mFilterOptions.GetAreaName(position));
                    filterItem.setChecked(true);
                } else {
                    mFilterSelections.areaSelections.remove(new Integer(position));
                    selectedAreaFilterItems.remove(mFilterOptions.GetAreaName(position));
                    filterItem.setChecked(false);
                }
                selectedAreaFilterItems.notifyDataSetChanged();
                UnrollListView.setListViewHeightBasedOnItems((ListView) selectedAreaItemsList);

                if (mFilterSelections.areaSelections.isEmpty()) {
                    selectedAreaItemsList.setVisibility(View.GONE);
                } else {
                    selectedAreaItemsList.setVisibility(View.VISIBLE);
                }
            }
        });

        // Apply Filter
        TextView applyFilter = (TextView)mRoot.findViewById(R.id.ApplyFilterButton);
        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                mCallback.onFilterSelectionChanged(mFilterSelections);
            }
        });

        mPoojaFilterSelect.performClick();
        openDrawer();
    }

    void ShowFilter (CheckedTextView filterType, View filter) {
        mPoojaFilterSelect.setChecked(false);
        mCasteFilterSelect.setChecked(false);
        mLanguageFilterSelect.setChecked(false);
        mAreaFilterSelect.setChecked(false);
        mDateFilterSelect.setChecked(false);
        mTimeFilterSelect.setChecked(false);
        filterType.setChecked(true);

        TextView filtersLabel = (TextView)mRoot.findViewById(R.id.SelectFiltersLabel);
        filtersLabel.setText(filterType.getText());

        mPoojaList.setVisibility(View.INVISIBLE);
        mCasteList.setVisibility(View.INVISIBLE);
        mLanguageList.setVisibility(View.INVISIBLE);
        mAreaList.setVisibility(View.INVISIBLE);
        filter.setVisibility(View.VISIBLE);
        int listHeight = UnrollListView.setListViewHeightBasedOnItems((ListView) filter);

        ViewGroup.LayoutParams layoutParams = mRoot.findViewById(R.id.FilterItems).getLayoutParams();
        layoutParams.height = listHeight + mRoot.findViewById(R.id.SelectFiltersLabel).getLayoutParams().height;
        mRoot.findViewById(R.id.FilterItems).setLayoutParams(layoutParams);

        ((ScrollView)mRoot.findViewById(R.id.PujariFilterScrollView)).smoothScrollTo(0, 0);
    }

    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener onDateSetListener;

        static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener onDateSetListener) {
            DatePickerFragment pickerFragment = new DatePickerFragment();
            pickerFragment.setOnDateSetListener(onDateSetListener);

            return pickerFragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
        }

        private void setOnDateSetListener(DatePickerDialog.OnDateSetListener listener) {
            this.onDateSetListener = listener;
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView selectedDateFilter = (TextView)mRoot.findViewById(R.id.SelectedDateFilter);
        String selectedDate = String.format("%d / %d / %d", day, month, year);
        selectedDateFilter.setText(selectedDate);
        selectedDateFilter.setVisibility(View.VISIBLE);

        mFilterSelections.dateSelection = selectedDate;
    }

    public static class TimePickerFragment extends DialogFragment {

        private TimePickerDialog.OnTimeSetListener onTimeSetListener;

        static TimePickerFragment newInstance(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
            TimePickerFragment pickerFragment = new TimePickerFragment();
            pickerFragment.setOnTimeSetListener(onTimeSetListener);

            return pickerFragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), onTimeSetListener, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        private void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener listener) {
            this.onTimeSetListener = listener;
        }

    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView selectedTimeFilter = (TextView)mRoot.findViewById(R.id.SelectedTimeFilter);
        String selectedTime = String.format("%d : %d %s", (hourOfDay <= 12 ? hourOfDay : hourOfDay % 12),
                minute, hourOfDay < 12 ? "AM" : "PM");
        selectedTimeFilter.setText(selectedTime);
        selectedTimeFilter.setVisibility(View.VISIBLE);

        mFilterSelections.timeSelection = String.format("%d:%d",hourOfDay,minute);
    }
}
