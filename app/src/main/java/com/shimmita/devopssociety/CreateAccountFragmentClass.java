package com.shimmita.devopssociety;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;

public class CreateAccountFragmentClass extends Fragment implements View.OnClickListener {
    //required empty fragment
    public CreateAccountFragmentClass() {
    }
//
    TextView textView_registration_preparations;
    Button button_register;
    FloatingActionButton floatingActionButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_create_account,container,false);
        //code here using Viewer mode
        textView_registration_preparations=view.findViewById(R.id.textViewContainingRegistrationMessage);
        textView_registration_preparations.setText(getString(R.string.registration_info_fragment).trim());
        //
        floatingActionButton=view.findViewById(R.id.floatingCreateAccount);
        floatingActionButton.setOnClickListener(this::onClick);
        //

       return view;
    }

    @Override
    public void onClick(View view) {
        Animation animation= AnimationUtils.loadAnimation(getActivity(),R.anim.abc_slide_in_top);
        floatingActionButton.startAnimation(animation);
        startActivity(new Intent(getActivity(),Registration.class));
        CustomIntent.customType(getActivity(),"fadein-to-fadeout");
        Toasty.custom(getActivity(), "Begin Registration", R.drawable.android2, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

    }
}
