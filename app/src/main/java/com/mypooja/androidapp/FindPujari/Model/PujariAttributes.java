package com.mypooja.androidapp.FindPujari.Model;

import com.mypooja.androidapp.FindPujari.View.PujariListing.DummyFilterOptions;

/**
 * Created by vinayhuddar on 08/09/15.
 */

public class PujariAttributes {
    Callback mCallback;

    public PujariAttributes(Callback callback) {
        mCallback = callback;
    }

    public class Model {
        PoojaTypes[] pooja_types;
        Area[] areas;
        Castes[] communities;
        Languages[] languages;
        Dates[] dates;
        Times[] times;

        class PoojaTypes {
            int id;
            String name;

            public PoojaTypes(int _id, String _name) {
                id = _id;
                name = _name;
            }
        }

        public String GetPoojaName(int idx) {
            return pooja_types[idx].name;
        }
        public String[] GetPoojaNames() {
            int numPoojaTypes = pooja_types.length;
            String[] poojaNames = new String[numPoojaTypes];
            for (int i = 0; i < numPoojaTypes; i++)
                poojaNames[i] = pooja_types[i].name;

            return poojaNames;
        }

        public int GetPoojaTypeId(int idx) {
            return pooja_types[idx].id;
        }

        class Area {
            int id;
            String name;

            public Area(int _id, String _name) {
                id = _id;
                name = _name;
            }
        }

        public String GetAreaName (int idx) {
            return areas[idx].name;
        }
        public String[] GetAreaNames() {
            int numAreas = areas.length;
            String[] areaNames = new String[numAreas];
            for (int i = 0; i < numAreas; i++)
                areaNames[i] = areas[i].name;

            return areaNames;
        }
        public int GetAreaId(int idx) {
            return areas[idx].id;
        }


        class Castes {
            int id;
            String name;

            public Castes(int _id, String _name) {
                id = _id;
                name = _name;
            }
        }

        public String GetCasteName (int idx) {
            return communities[idx].name;
        }
        public String[] GetCommunityNames() {
            int numCommunities = communities.length;
            String[] communityNames = new String[numCommunities];
            for (int i = 0; i < numCommunities; i++)
                communityNames[i] = communities[i].name;

            return communityNames;
        }

        public int GetCommunityId(int idx) {
            return communities[idx].id;
        }

        class Languages {
            int id;
            String name;

            public Languages(int _id, String _name) {
                id = _id;
                name = _name;
            }
        }

        public String GetLanguageName (int idx) {
            return languages[idx].name;
        }
        public String[] GetLanguageNames() {
            int numLanguages = languages.length;
            String[] languageNames = new String[numLanguages];
            for (int i = 0; i < numLanguages; i++)
                languageNames[i] = languages[i].name;

            return languageNames;
        }

        public int GetLanguageId(int idx) {
            return languages[idx].id;
        }

        class Dates {
            int id;
            String name;
            String date;

            public Dates(int _id, String _name) {
                id = _id;
                name = _name;
                date = _name;
            }
        }

        public String[] GetDates() {
            int numDates = dates.length;
            String[] dateNames = new String[numDates];
            for (int i = 0; i < numDates; i++)
                dateNames[i] = dates[i].name;

            return dateNames;
        }

        public int GetDateId(int idx) {
            return dates[idx].id;
        }

        class Times {
            int id;
            String name;
            String time;

            public Times(int _id, String _name) {
                id = _id;
                name = _name;
                time = _name;
            }
        }

        public String GetTimeName (int idx) {
            return times[idx].name;
        }
        public String[] GetTimes() {
            int numTimes = times.length;
            String[] timeNames = new String[numTimes];
            for (int i = 0; i < numTimes; i++)
                timeNames[i] = times[i].name;

            return timeNames;
        }

        public int GetTimeId(int idx) {
            return times[idx].id;
        }


        // To be deleted later
        public Model(String[] poojaNames, String[] areaNames, String[] casteNames,
                     String[] languageNames, String[] dateNames, String[] timeNames) {
            int numNames = poojaNames.length;
            pooja_types = new PoojaTypes[numNames];
            for (int i = 0; i < numNames; i++)
                pooja_types[i] = new PoojaTypes(i, poojaNames[i]);

            numNames = areaNames.length;
            areas = new Area[numNames];
            for (int i = 0; i < numNames; i++)
                areas[i] = new Area(i, areaNames[i]);

            numNames = casteNames.length;
            communities = new Castes[numNames];
            for (int i = 0; i < numNames; i++)
                communities[i] = new Castes(i, casteNames[i]);

            numNames = languageNames.length;
            languages = new Languages[numNames];
            for (int i = 0; i < numNames; i++)
                languages[i] = new Languages(i, languageNames[i]);

            numNames = dateNames.length;
            dates = new Dates[numNames];
            for (int i = 0; i < numNames; i++)
                dates[i] = new Dates(i, dateNames[i]);

            numNames = timeNames.length;
            times = new Times[numNames];
            for (int i = 0; i < numNames; i++)
                times[i] = new Times(i, timeNames[i]);
        }
    }

    int retryCount = 0;
    public void FetchFilters () {
        /*MyPoojaApplication.GetAPIService().GetPoojariSelectionFilters(new retrofit.Callback<Model>() {
            @Override
            public void success(Model categoryData, Response response) {
                mCallback.OnPoojariSelectionFiltersReceived(categoryData);

                retryCount = 0;
            }

            @Override
            public void failure(RetrofitError error) {
                // Attempt five retries before giving up
                retryCount++;
                if (retryCount < CommonDefinitions.RETRY_COUNT)
                    FetchFilters();
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

        mCallback.OnPoojariSelectionFiltersReceived(new Model(DummyFilterOptions.poojaNames, DummyFilterOptions.areaNames,
                DummyFilterOptions.casteNames, DummyFilterOptions.languageNames, DummyFilterOptions.monthNames, DummyFilterOptions.timeNames));
    }

    public interface Callback {
        public void OnPoojariSelectionFiltersReceived(Model filterData);
    }
}
