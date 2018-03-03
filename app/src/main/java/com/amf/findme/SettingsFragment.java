package com.amf.findme;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public SettingsFragment() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        for (String key : sharedPreferences.getAll().keySet()) {
            onSharedPreferenceChanged(sharedPreferences, key);
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (key.equals("password")) {
            preference.setSummary(sharedPreferences.getString(key, "").replaceAll(".", "*"));
        }
        else {
            preference.setSummary(sharedPreferences.getString(key, ""));
        }
    }

}