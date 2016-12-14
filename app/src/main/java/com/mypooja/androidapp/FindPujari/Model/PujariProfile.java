package com.mypooja.androidapp.FindPujari.Model;

/**
 * Created by vinayhuddar on 12/09/15.
 */
public class PujariProfile {
    Callback mCallback;
    public PujariProfile (Callback callback) {
        mCallback = callback;

    }

    public interface Callback {
        public void OnPujariProfileReceived (Model profileData);
    }

    public class Model {
        int pujari_id;
        String name;
        String email;
        String telephone;
        int age;
        double experience;
        int gender;
        int community_id;
        int[] language_ids;
        int[] area_ids;
        String avatar;
        String company;
        String banner;
        String description;
        double rating_avg;
        int rating_count;
        int review_count;
        String area;
        String city;

        public int GetPujariId () { return pujari_id; }
        public String GetName () { return name; }
        public String GetEmail () { return email; }
        public String GetTelephone () { return telephone; }
        public int GetAge () { return age; }
        public double GetExperience () { return experience; }
        public int GetGender () { return gender; }
        public int GetCommunityId () { return community_id; }
        public int[] GetLanguageIds () { return language_ids; }
        public int[] GetAreaIds () { return area_ids; }
        public String GetAvatar () { return avatar; }
        public String GetCompany () { return company; }
        public String GetBanner () { return banner; }
        public String GetDescription () { return description; }
        public String GetArea () { return area; }
        public double GetRating () { return rating_avg; }
        public int GetRatingCount () { return rating_count; }
        public int GetReviewCount () { return review_count; }
        public String GetCity () { return city; }

        public Model (int _pujari_id,
                String _name,
                String _email,
                String _telephone,
                int _age,
                double _experience,
                int _gender,
                int _community_id,
                int[] _language_ids,
                int[] _area_ids,
                String _avatar,
                String _company,
                String _banner,
                String _description,
                double _rating,
                int _rating_count,
                int _review_count,
                String _area,
                String _city) {
            pujari_id = _pujari_id;
            name = _name;
            email = _email;
            age = _age;
            experience = _experience;
            gender = _gender;
            community_id = _community_id;
            language_ids = _language_ids;
            area_ids = _area_ids;
            avatar = _avatar;
            company = _company;
            banner = _banner;
            description = _description;
            area = _area;
            rating_avg = _rating;
            rating_count = _rating_count;
            review_count = _review_count;
            city = _city;
        }
    }

    int retryCount = 0;
    public void FetchPujariProfile (final int pujariId) {
        /*MyPoojaApplication.GetAPIService().GetPoojariProfile(pujariId, new retrofit.Callback<PujariProfile.Model>() {
            @Override
            public void success(PujariProfile.Model model, Response response) {
                mCallback.OnPujariProfileReceived(model);
            }

            @Override
            public void failure(RetrofitError error) {
                // Attempt five retries before giving up
                retryCount++;
                if (retryCount < CommonDefinitions.RETRY_COUNT)
                    FetchPujariProfile(pujariId);
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

        int[] languageIds = {0, 1, 2};
        int[] areaIds = {0, 1, 2};
        mCallback.OnPujariProfileReceived((new Model(1, "Sharana Basappa", "Sharana.Basappa@gmail.com", "12345678",
                37, 20, 0, 0, languageIds, areaIds, null, "Kudala Sangama", null,
                "I have been doing vairous types of Poojas since the age of 18. I am a devotee of Basavanna " +
                "and affiliated to Kudala Sangama.", 4.2, 982, 420, "Vijayanagar", "Bangalore")));
    }
}
