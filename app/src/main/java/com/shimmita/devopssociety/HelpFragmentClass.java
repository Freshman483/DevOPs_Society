package com.shimmita.devopssociety;

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

import es.dmoral.toasty.Toasty;

public class HelpFragmentClass extends Fragment implements View.OnClickListener {
    //Required Empty constructor
    public HelpFragmentClass() {
    }
    //

    Button button;
    TextView textView,textViewTitle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_help,container,false);
        //code here using Viewer mode
        textView=view.findViewById(R.id.textViewContainingHelpMessage);
        textViewTitle=view.findViewById(R.id.textViewHelpTitle);
        button=view.findViewById(R.id.buttonReadLoudForMe);
        button.setOnClickListener(this::onClick);
        //

       return view;
    }

    @Override
    public void onClick(View view) {
        Toasty.custom(getActivity(), "Loading....", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_SHORT, true, true).show();
        Animation animation= AnimationUtils.loadAnimation(getActivity(),R.anim.abc_slide_in_bottom);
        button.startAnimation(animation);
        new SpeechClass(getActivity(),textViewTitle.getText().toString()+","+textView.getText().toString());
    }
}
