package com.litto.expense;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by tom on 2016/11/21.
 */

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
