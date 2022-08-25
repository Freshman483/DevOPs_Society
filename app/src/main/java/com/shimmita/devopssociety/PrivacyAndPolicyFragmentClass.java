package com.shimmita.devopssociety;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PrivacyAndPolicyFragmentClass extends Fragment {

    TextView textView_display_privacy_policy;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_privacy_policy,container,false);
        //code here using Viewer mode
        textView_display_privacy_policy=view.findViewById(R.id.textview_display_privacy_policy);
        textView_display_privacy_policy.setText(getString(R.string.privacy_and_policy));
        //

       return  view;
    }
}
