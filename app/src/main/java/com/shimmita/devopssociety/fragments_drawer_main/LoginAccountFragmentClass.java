package com.shimmita.devopssociety.fragments_drawer_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shimmita.devopssociety.mains.Login;
import com.shimmita.devopssociety.R;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;

public class LoginAccountFragmentClass extends Fragment implements View.OnClickListener {

    TextView textView_login_preparations;
    FloatingActionButton floatingActionButtonLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        //code here using Viewer mode
        textView_login_preparations = view.findViewById(R.id.textViewContainingLoginMessage);
        textView_login_preparations.setText(getString(R.string.login_information).trim());
        //
        floatingActionButtonLogin = view.findViewById(R.id.floatingLoginAccount);
        floatingActionButtonLogin.setOnClickListener(this::onClick);
        //

        return view;
    }

    @Override
    public void onClick(View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_in_top);
        floatingActionButtonLogin.startAnimation(animation);
        startActivity(new Intent(getActivity(), Login.class));
        CustomIntent.customType(getActivity(), "fadein-to-fadeout");
        Toasty.custom(getActivity(), "Begin Registration", R.drawable.android2, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

    }
}
