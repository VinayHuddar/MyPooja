package com.mypooja.androidapp.FindPujari.View.PujariListing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mypooja.androidapp.Common.Controller.ImageDownloader;
import com.mypooja.androidapp.Common.IntentActionStrings;
import com.mypooja.androidapp.FindPujari.Model.PujariAttributes;
import com.mypooja.androidapp.FindPujari.Model.PujariList;
import com.mypooja.androidapp.FindPujari.View.PujariProfile.PujariProfileScreen;
import com.mypooja.androidapp.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by vinayhuddar on 10/09/15.
 */
public class PujariListAdapter extends ArrayAdapter {
    ArrayList<PujariList.Pujari> mPujariList;
    PujariAttributes.Model mFilterOptions;
    Activity mActivity;
    ImageDownloader mImageDownloader;

    PujariList mDummyPujariListObj;

    public PujariListAdapter (Activity activity, int resource, PujariList.Model pujariList, PujariAttributes.Model filterOptions) {
        super(activity, resource);

        mActivity = activity;
        mPujariList = new ArrayList<>(Arrays.asList(pujariList.GetPujariList()));
        mFilterOptions = filterOptions;

        mImageDownloader = new ImageDownloader();
        mDummyPujariListObj = new PujariList(null);
    }

    public int getCount () {
        return mPujariList.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View currListViewItem;
        if (convertView == null) {
            currListViewItem = inflater.inflate(R.layout.layout_pujari_list_item, null);
        } else {
            currListViewItem = (View) convertView;
        }

        final PujariList.Pujari currDataItem = mPujariList.get(position);

        currListViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, PujariProfileScreen.class);
                intent.putExtra(IntentActionStrings.PUJARI_ID, currDataItem.GetId());
                intent.putExtra(IntentActionStrings.COMMUNITY, mFilterOptions.GetCommunityNames());
                intent.putExtra(IntentActionStrings.LANGUAGES, mFilterOptions.GetLanguageNames());
                intent.putExtra(IntentActionStrings.AREAS, mFilterOptions.GetAreaNames());
                mActivity.startActivity(intent);
            }
        });

        ImageView photo = (ImageView)currListViewItem.findViewById(R.id.Photo);
        if (currDataItem.GetImageURL() != null)
            mImageDownloader.download(currDataItem.GetImageURL(), photo);
        else
            photo.setImageResource(R.drawable.pujari);

        TextView name = (TextView)currListViewItem.findViewById(R.id.Name);
        name.setText(currDataItem.GetName());

        TextView caste = (TextView)currListViewItem.findViewById(R.id.Caste);
        caste.setText(mFilterOptions.GetCasteName(currDataItem.GetCasteId()));

        TextView age = (TextView)currListViewItem.findViewById(R.id.Age);
        age.setText(String.valueOf(currDataItem.GetAge()));

        TextView experience = (TextView)currListViewItem.findViewById(R.id.Experience);
        experience.setText(String.valueOf(currDataItem.GetExperience()));

        TextView rating = (TextView)currListViewItem.findViewById(R.id.RatingImage);
        rating.setText(String.format("%.1f / 5", currDataItem.GetRating()));

        TextView languages = (TextView)currListViewItem.findViewById(R.id.Languages);
        final int[] langIds = currDataItem.GetLanguageIds();
        String langList = mFilterOptions.GetLanguageName(langIds[0]);
        for (int i = 1; i < langIds.length; i++) {
            langList = String.format("%s, %s", langList, mFilterOptions.GetLanguageName(langIds[i]));
        }
        languages.setText(langList);

        TextView areas = (TextView)currListViewItem.findViewById(R.id.Areas);
        final int[] areaIds = currDataItem.GetAreaIds();
        String areaList = mFilterOptions.GetAreaName(areaIds[0]);
        for (int i = 1; i < areaIds.length; i++) {
            areaList = String.format("%s, %s", areaList, mFilterOptions.GetAreaName(areaIds[i]));
        }
        areas.setText(areaList);

        //TextView city = (TextView)currListViewItem.findViewById(R.id.City);
        //city.setText("Bangalore");

        return currListViewItem;
    }

    public long getItemId (int position) {
        return mPujariList.get(position).GetId();
    }

    public PujariList.Pujari getItem (int position) {
        return mPujariList.get(position);
    }

    public void ReInitializeList (PujariList.Model model) {
        mPujariList = new ArrayList<>(Arrays.asList(model.GetPujariList()));
        notifyDataSetChanged();
    }
}
