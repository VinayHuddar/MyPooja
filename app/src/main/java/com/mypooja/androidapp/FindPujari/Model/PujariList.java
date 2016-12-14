package com.mypooja.androidapp.FindPujari.Model;

import com.mypooja.androidapp.Common.CommonDefinitions;
import com.mypooja.androidapp.Common.Controller.ImageDownloader;
import com.mypooja.androidapp.MyPoojaApplication;

import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vinayhuddar on 10/09/15.
 */
public class PujariList {
    Callback mCallback;

    public PujariList (Callback callback) {
        mCallback = callback;
    }

    public class Model {
        int pujari_total;
        Pujari[] pujaris;

        public int GetPujariCount () {
            return pujari_total;
        }

        public Pujari[] GetPujariList () {
            return pujaris;
        }
        public Model (int total, Pujari[] pujariList) {
            pujari_total = total;
            pujaris = pujariList;
        }
    }

    public class Pujari {
        int id;
        String image;
        String name;
        int age;
        int caste_id;
        int[] language_ids;
        double experience;
        double rating;
        int[] area_ids;
        int city_id;

        public int GetId () { return id; }
        public String GetImageURL () { return image; }
        public int GetAge () { return age; }
        public String GetName () { return name; }
        public int GetCasteId () { return caste_id; }
        public int[] GetLanguageIds () { return language_ids; }
        public double GetExperience () { return experience; }
        public double GetRating() { return rating; }
        public int[] GetAreaIds () { return area_ids; }
        public int GetCityId () { return city_id; }

        public Pujari ( int _id,
                        String _image,
                        String _name,
                        int _age,
                        int _caste_id,
                        int[] _language_ids,
                        double _experience,
                        double _rating,
                        int[] _area_ids,
                        int _city_id) {
            id = _id;
            image = _image;
            name = _name;
            age = _age;
            caste_id = _caste_id;
            language_ids = _language_ids;
            experience = _experience;
            rating = _rating;
            area_ids = _area_ids;
            city_id = _city_id;
        }
    }

    /*public Pujari GetPujariInstance (int id,
            String image,
            String name,
            int age,
            int caste_id,
            int[] language_ids,
            double experience,
            double rating_avg,
            int[] area_ids,
            int city_id ) {
        return (new Pujari (id, image, name, age, caste_id, language_ids, experience, rating_avg, area_ids, city_id));
    }*/

    int retryCount = 0;
    public void FetchPujariList (final Map<String, String> queries) {
        /*MyPoojaApplication.GetAPIService().GetPoojariList(queries, new retrofit.Callback<PujariList.Model>() {
            @Override
            public void success(PujariList.Model model, Response response) {
                mCallback.OnListReceived(model);
            }

            @Override
            public void failure(RetrofitError error) {
                // Attempt five retries before giving up
                retryCount++;
                if (retryCount < CommonDefinitions.RETRY_COUNT)
                    FetchPujariList(queries);
                else {
                    // Show a "Network Problem" message to user and take him to landing screen
                    retryCount = 0;
                        //Toast.makeText(mActivity, "There seems to be some problem with the network - " +
                        //        "either you are not connected to the network or the network is too slow or ",
                        //        Toast.LENGTH_SHORT);
                        //Intent intent = new Intent(mActivity, IntegratedServicesScreen.class);
                        //mActivity.startActivity(intent);
                }
            }
        });*/

        int dummyPujariCnt = 50;
        Pujari[] dummyPujariList = new Pujari[dummyPujariCnt];
        for (int i = 0; i < dummyPujariCnt; i++) {
            int count = (i % 5) + 1;
            int[] dummyLangIds = new int[count];
            int[] dummyAreaIds = new int[count];
            for (int j = 0; j < count; j++) {
                dummyLangIds[j] = (i % 16) + j;
                dummyAreaIds[j] = j;
            }
            dummyPujariList[i] = new Pujari(i, null, "ABCD",
                    20 + i, i % 3, dummyLangIds, i % 30, 1 + (i % 21) * 0.2, dummyAreaIds, 0);
        }

        mCallback.OnListReceived((new Model(dummyPujariCnt, dummyPujariList)));

    }

    public interface Callback {
        public void OnListReceived (Model list);
    }
}
