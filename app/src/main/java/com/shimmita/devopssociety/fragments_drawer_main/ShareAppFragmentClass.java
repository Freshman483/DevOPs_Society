package com.shimmita.devopssociety.fragments_drawer_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shimmita.devopssociety.R;

public class ShareAppFragmentClass extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_share_app,container,false);

        //code here using Viewer mode
        startActivity(new Intent(Intent.ACTION_SEND).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).putExtra("Details","Share The App And Expand DevOps  Society Kenya")
                .setType("text/plain"));
        //

        return view;
    }
}
