package com.mypooja.androidapp.FindPujari.Model;

/**
 * Created by vinayhuddar on 10/09/15.
 */
public class PujaList {
    Callback mCallback;

    public PujaList(Callback callback) {
        mCallback = callback;
    }

    public class Model {
        PujaCategories[] pujaCats;

        public class PujaCategories {
            String catName;
            Puja[] pujas;

            public PujaCategories(String _puja_type, Puja[] _pujas) {
                catName = _puja_type;
                pujas = _pujas;
            }
        }

        public class Puja {
            int id;
            String name;
            String image;

            public Puja (int _id,
                         String _name,
                         String _image) {
                id = _id;
                name = _name;
                image = _image;
            }

            public String GetPujaName () { return name; }

            public String GetPujaImage () { return image; }
        }

        public int GetCategoryCount() { return pujaCats.length; }
        public String GetCategoryName (int catId) { return pujaCats[catId].catName; }
        public Puja[] GetPujaList (int catId) { return pujaCats[catId].pujas; }

        public Model() {
            pujaCats = new PujaCategories[2];
            Puja[] occPujas = new Puja[6];
            int i;
            String[] pujaNames = {"Dusshera Puja", "Diwali Puja", "Naag Panchami Puja", "Birthday Puja",
                    "Mundan Puja", "Namakaran Puja", "Mangal Dosh Nivrati Puja", "Gayatri Jap Puja",
                    "Sidda Dhanteras Puja"};
            String[] pujaImageNames = {"dusshera_puja", "diwali_puja", "naag_panchami_puja", "birthday_puja",
                    "mundan_puja", "namakaran_puja", "mangal_dosh_nivrati_puja", "gayatri_jap_puja",
                    "sidda_dhanteras_puja"};
            for (i = 0; i < 6; i++) {
                occPujas[i] = new Puja(i + 1, pujaNames[i], pujaImageNames[i]);
            }
            pujaCats[0] = new PujaCategories("Poojas for Occassions", occPujas);

            Puja[] probPujas = new Puja[pujaNames.length - i];
            for (int j = 0; i < pujaNames.length; j++, i++) {
                probPujas[j] = new Puja(i + 1, pujaNames[i], pujaImageNames[i]);
            }

            pujaCats[1] = new PujaCategories("Poojas for Problems", probPujas);
        }
    }

    int retryCount = 0;
    public void FetchPujaList () {
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

        mCallback.OnPujaListReceived((new Model()));
    }

    public interface Callback {
        public void OnPujaListReceived(Model list);
    }
}
