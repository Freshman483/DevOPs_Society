package com.shimmita.devopssociety.fragments_loggedin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shimmita.devopssociety.R;

public class JobsLoggedInFragmentClass extends Fragment {

    //empty constructor required
    public JobsLoggedInFragmentClass() {
    }
    //


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.jobs_loggedin_fragment,container,false);
        //code here


        //

        return view;
    }
}
