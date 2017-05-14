package com.savior.notes.popularmovies.data;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import com.savior.notes.popularmovies.R;

/**
 * Created by Orlando on 5/12/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_movie);
        PreferenceScreen prefScreen = getPreferenceScreen();
        setPrefSummary(prefScreen);
        prefScreen.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }


    private void setPrefSummary(PreferenceScreen prefScreen){
        SharedPreferences sharedPreferences = prefScreen.getSharedPreferences();
        for(int i=0; i < prefScreen.getPreferenceCount(); i++) {
            Preference p = prefScreen.getPreference(i);
            if (p instanceof ListPreference) {

                ListPreference list = (ListPreference) p;
                String value = sharedPreferences.getString(p.getKey(), "");
                int prefIndex = list.findIndexOfValue(value);
                if (prefIndex >= 0) {
                    list.setSummary(list.getEntries()[prefIndex]);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        setPrefSummary(getPreferenceScreen());
    }
}
