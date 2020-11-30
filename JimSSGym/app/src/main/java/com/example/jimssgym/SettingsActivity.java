package com.example.jimssgym;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences logOut_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //
//        logOut_button = findViewById(R.id.logOut_button);
//        onPreferenceClick().onPreferenceClick(findPreference("about"));
//        logOut_button = getSharedPreferences("logout_button", 0);
//        onPreferenceClick().onPreferenceClick((Preference) logOut_button);
//        Preference test = PreferenceManager.getDefaultSharedPreferences(logOut_button);
//        test.getOnPreferenceClickListener()
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

//    private static Preference.OnPreferenceClickListener onPreferenceClick()
//    {
//        return new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference)
//            {
//                FirebaseAuth.getInstance().signOut();
//                return true;
//            }
//        };
//    }
}