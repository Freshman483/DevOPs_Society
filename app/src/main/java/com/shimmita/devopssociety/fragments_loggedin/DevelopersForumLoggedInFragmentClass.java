package com.shimmita.devopssociety.fragments_loggedin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shimmita.devopssociety.R;

public class DevelopersForumLoggedInFragmentClass extends Fragment {

    //empty constructor required
    public DevelopersForumLoggedInFragmentClass() {
    }
    //


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.developers_forum_loggedin_fragment,container,false);
        //code here


        //

        return view;
    }
}
