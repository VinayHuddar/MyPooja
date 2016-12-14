package com.mypooja.androidapp.FindPujari.View.PujariProfile;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mypooja.androidapp.Common.CommonDefinitions;
import com.mypooja.androidapp.Common.Controller.ImageDownloader;
import com.mypooja.androidapp.Common.IntentActionStrings;
import com.mypooja.androidapp.Common.View.SlidingTabLayout;
import com.mypooja.androidapp.FindPujari.Model.PujaList;
import com.mypooja.androidapp.FindPujari.Model.PujariProfile;
import com.mypooja.androidapp.R;

/**
 * Created by vinayhuddar on 12/09/15.
 */
public class PujariProfileScreen extends AppCompatActivity implements PujariProfile.Callback, PujaList.Callback {
    static final int PUJA_LIST_FRAGMENT = 0;
    static final int REVIEWS_LIST_FRAGMENT = 1;

    ImageDownloader mImageDownloader;

    int mPujariId;
    String[] mCommunity;
    String[] mLanguages;
    String[] mAreas;

    PujariProfile.Model mProfileData = null;
    PujaList.Model mPujaList = null;

    PujariProfilePagerAdapter mPujariProfilePagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pujari_profile);

        mImageDownloader = new ImageDownloader();

        mPujariId = getIntent().getIntExtra(IntentActionStrings.PUJARI_ID, CommonDefinitions.INVALID_DATA);
        mCommunity = getIntent().getStringArrayExtra(IntentActionStrings.COMMUNITY);
        mLanguages = getIntent().getStringArrayExtra(IntentActionStrings.LANGUAGES);
        mAreas = getIntent().getStringArrayExtra(IntentActionStrings.AREAS);

        findViewById(R.id.loading_message).setVisibility(View.VISIBLE);

        PujariProfile pujariProfile = new PujariProfile(this);
        pujariProfile.FetchPujariProfile(mPujariId);

        PujaList pujaList = new PujaList(this);
        pujaList.FetchPujaList();
    }

    public void OnPujariProfileReceived (PujariProfile.Model profileData) {
        findViewById(R.id.loading_message).setVisibility(View.INVISIBLE);

        mProfileData = profileData;

        ImageView photo = (ImageView) findViewById(R.id.Image);
        if (profileData.GetAvatar() != null)
            mImageDownloader.download(profileData.GetAvatar(), photo);
        else
            photo.setImageResource(R.drawable.poojari);

        TextView name = (TextView) findViewById(R.id.Name);
        name.setText(profileData.GetName());

        TextView community = (TextView) findViewById(R.id.Community);
        community.setText(mCommunity[profileData.GetCommunityId()]);

        TextView aboutMe = (TextView) findViewById(R.id.AboutLabel);
        aboutMe.setPaintFlags(aboutMe.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView age = (TextView) findViewById(R.id.Age);
        age.setText(String.valueOf(profileData.GetAge()));

        TextView experience = (TextView) findViewById(R.id.Experience);
        experience.setText(String.valueOf(profileData.GetExperience()));

        TextView languages = (TextView) findViewById(R.id.Languages);
        final int[] langIds = profileData.GetLanguageIds();
        String langList = mLanguages[langIds[0]];
        for (int i = 1; i < langIds.length; i++) {
            langList = String.format("%s, %s", langList, mLanguages[langIds[i]]);
        }
        languages.setText(langList);

        TextView areas = (TextView) findViewById(R.id.Areas);
        final int[] areaIds = profileData.GetAreaIds();
        String areaList = mAreas[areaIds[0]];
        for (int i = 1; i < areaIds.length; i++) {
            areaList = String.format("%s, %s", areaList, mAreas[areaIds[i]]);
        }
        areas.setText(areaList);

        TextView affiliation = (TextView) findViewById(R.id.Affiliation);
        affiliation.setText(profileData.GetCompany());

        TextView address = (TextView) findViewById(R.id.Address);
        address.setText(String.format("%s, %s", profileData.GetArea(), profileData.GetCity()));

        TextView email = (TextView) findViewById(R.id.Email);
        email.setText(profileData.GetEmail());

        int numStars = ((int) profileData.GetRating());
        for (int i = 0; i < CommonDefinitions.NUM_RATING_STARS; i++) {
            ImageView starImage = null;
            switch (i) {
                case 0:
                    starImage = (ImageView) findViewById(R.id.RatingStar1);
                    break;
                case 1:
                    starImage = (ImageView) findViewById(R.id.RatingStar2);
                    break;
                case 2:
                    starImage = (ImageView) findViewById(R.id.RatingStar3);
                    break;
                case 3:
                    starImage = (ImageView) findViewById(R.id.RatingStar4);
                    break;
                case 4:
                    starImage = (ImageView) findViewById(R.id.RatingStar5);
                    break;
            }
            starImage.setImageDrawable(getResources().getDrawable(i < numStars ? R.drawable.rating_16_purple : R.drawable.rating_16));
        }

        TextView ratingCnt = (TextView) findViewById(R.id.RatingCount);
        ratingCnt.setText(String.valueOf(profileData.GetRatingCount()));

        TextView reviewCnt = (TextView) findViewById(R.id.Reviews);
        reviewCnt.setText(String.format("%d  ", profileData.GetReviewCount()));
        reviewCnt.setPaintFlags(reviewCnt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView reviewsLabel = (TextView) findViewById(R.id.ReviewsLabel);
        reviewsLabel.setPaintFlags(reviewsLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView about = (TextView) findViewById(R.id.AboutPujari);
        about.setText(profileData.GetDescription());

        SetupProfilePager();
    }

    public void OnPujaListReceived (PujaList.Model pujaList) {
        mPujaList = pujaList;

        SetupProfilePager();
    }

    void SetupProfilePager () {
        if ((mProfileData != null) && (mPujaList != null)) {
            mPujariProfilePagerAdapter = new PujariProfilePagerAdapter(getSupportFragmentManager(), this);
            mViewPager = (ViewPager) findViewById(R.id.PujariProfilePager);
            mViewPager.setAdapter(mPujariProfilePagerAdapter);
            mViewPager.setOnTouchListener(new View.OnTouchListener() {
                // Setting on Touch Listener for handling the touch inside ScrollView
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // How far the user has to scroll before it locks the parent vertical scrolling.
                    final int margin = 10;
                    final int fragmentOffset = v.getScrollX() % v.getWidth();

                    // Disallow the touch request for parent scroll on touch of child view
                    if (fragmentOffset > margin && fragmentOffset < v.getWidth() - margin) {
                        mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
                    }

                    return false;
                }
            });

            // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
            // it's PagerAdapter set.
            SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.SlidingTabs);
            mSlidingTabLayout.setDistributeEvenly(true);
            mSlidingTabLayout.setViewPager(mViewPager);
        }
    }

    public class PujariProfilePagerAdapter extends FragmentPagerAdapter {
        Context mContext;

        public PujariProfilePagerAdapter(FragmentManager fm, Context context) {
            super(fm);

            mContext = context;
        }

        @Override
        public Fragment getItem(int i) {
            Bundle args = new Bundle();
            args.putInt(IntentActionStrings.PUJARI_ID, mPujariId);

            Fragment fragment = PujaListFragment.newInstance(mPujaList, i);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public int getCount() {
            return mPujaList.GetCategoryCount();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ( mPujaList.GetCategoryName(position));
        }
    }

    public static class PujaListFragment extends Fragment {
        PujaList.Model mPujaList;
        int mCatId;

        public static PujaListFragment newInstance(PujaList.Model profileData, int catId) {
            PujaListFragment instance = new PujaListFragment();
            instance.SetProfileData(profileData, catId);

            return instance;
        }

        void SetProfileData (PujaList.Model profileData, int catId) {
            mCatId = catId;
            mPujaList = profileData;
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(
                    R.layout.fragment_pujari_pujas_list, container, false);
            int pujariId = getArguments().getInt(IntentActionStrings.PUJARI_ID);

            rootView.findViewById(R.id.loading_message).setVisibility(View.INVISIBLE);

            GridView pujaGrid = (GridView)rootView.findViewById(R.id.PujaGrid);
            PujaGridAdapter gridAdapter = new PujaGridAdapter(mPujaList.GetPujaList(mCatId));
            pujaGrid.setAdapter(gridAdapter);
            pujaGrid.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // How far the user has to scroll before it locks the parent vertical scrolling.
                    ((ScrollView) getActivity().findViewById(R.id.PujariProfileScrollView)).requestDisallowInterceptTouchEvent(true);

                    return false;
                }
            });

            return rootView;
        }

        public class PujaGridAdapter extends BaseAdapter {

            PujaList.Model.Puja[] mPujaList;
            public PujaGridAdapter (PujaList.Model.Puja[] pujaList) {
                mPujaList = pujaList;

            }

            public View getView (int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View currView;
                if (convertView == null) {
                    currView = inflater.inflate(R.layout.layout_puja_grid_item, null);;
                } else {
                    currView = (View) convertView;
                }

                ImageView pujaImage = (ImageView)currView.findViewById(R.id.Image);
                int imageResource = getResources().getIdentifier(String.format("@drawable/%s",
                        mPujaList[position].GetPujaImage()), null, getActivity().getPackageName());
                pujaImage.setImageDrawable(getActivity().getResources().getDrawable(imageResource));

                TextView pujaName = (TextView)currView.findViewById(R.id.Name);
                pujaName.setText(mPujaList[position].GetPujaName());

                DisallowScrollView(currView);
                DisallowScrollView(pujaImage);
                DisallowScrollView(pujaName);

                return currView;
            }

            void DisallowScrollView (View view) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // How far the user has to scroll before it locks the parent vertical scrolling.
                        ((ScrollView) getActivity().findViewById(R.id.PujariProfileScrollView)).requestDisallowInterceptTouchEvent(true);

                        return false;
                    }
                });
            }

            public int getCount() { return mPujaList.length; }

            public long getItemId(int position) { return position; }

            public PujaList.Model.Puja getItem(int position) { return mPujaList[position]; }
        }
    }
}
