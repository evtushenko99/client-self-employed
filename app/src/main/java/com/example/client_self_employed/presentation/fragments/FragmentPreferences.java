package com.example.client_self_employed.presentation.fragments;

import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.client_self_employed.R;

public class FragmentPreferences extends PreferenceFragmentCompat {
    public static FragmentPreferences newInstance() {
        return new FragmentPreferences();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, null);
        Preference notification = findPreference(getString(R.string.preferences_enable_notifications_switch_key));
        if (notification != null) {
            notification.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Toast.makeText(requireContext(), "Ура", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
}
