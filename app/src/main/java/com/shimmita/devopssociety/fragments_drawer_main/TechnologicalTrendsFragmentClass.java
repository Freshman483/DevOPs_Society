package com.shimmita.devopssociety.fragments_drawer_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shimmita.devopssociety.R;

public class TechnologicalTrendsFragmentClass extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //code here using Viewer mode

        //

       return inflater.inflate(R.layout.fragment_technology_trends,container,false);
    }
}
