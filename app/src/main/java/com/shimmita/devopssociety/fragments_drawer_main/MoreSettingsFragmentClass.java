package com.shimmita.devopssociety.fragments_drawer_main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.shimmita.devopssociety.R;

public class MoreSettingsFragmentClass extends Fragment {

    public static final String TAG = "MoreSettingsFragmentClass";
    String TITLE_SHARED_PREFERENCES = "shared Preferences";
    String SWITCH_STATUS = "switchStatus";

    AppCompatSeekBar seekBarFontChanger;
    //
    SwitchCompat switchCompatThemeChanger;

    //empty constructor required
    public MoreSettingsFragmentClass() {
    }

    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_settings, container, false);
        //code here using Viewer mode
        seekBarFontChanger = view.findViewById(R.id.seekBarChangeFont);
        switchCompatThemeChanger = view.findViewById(R.id.switchChangeTheme);


        //switchCheckFunctionUpdateUI

        switchUpdateUIAccordingly();
        //


        switchCompatThemeChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                functionCheckSwitch();
            }
        });


        //


        return view;
    }

    private void switchUpdateUIAccordingly() {
        if (switchCompatThemeChanger.isChecked()) {
            Toast.makeText(getActivity(), "Dark Mode", Toast.LENGTH_SHORT).show();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    private void functionCheckSwitch() {
        if (switchCompatThemeChanger.isChecked()) {
            Toast.makeText(getActivity(), "Dark Mode", Toast.LENGTH_SHORT).show();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //initialisation of the shared preferences
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(TITLE_SHARED_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(SWITCH_STATUS, switchCompatThemeChanger.isChecked());
            editor.apply();

            //
        } else {

            Toast.makeText(getActivity(), "Light Mode", Toast.LENGTH_SHORT).show();
            //
        }
    }

}
