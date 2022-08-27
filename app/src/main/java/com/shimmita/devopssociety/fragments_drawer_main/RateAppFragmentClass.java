package com.shimmita.devopssociety.fragments_drawer_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shimmita.devopssociety.R;

import java.util.Random;

import es.dmoral.toasty.Toasty;

public class RateAppFragmentClass extends Fragment implements View.OnClickListener {
    //
    ImageView imageViewLinuxDoll;

    //empty constructor required
    public RateAppFragmentClass() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_app, container, false);
        //code here using Viewer mode

        imageViewLinuxDoll = view.findViewById(R.id.image_linux_doll);
        imageViewLinuxDoll.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_top));
        imageViewLinuxDoll.setOnClickListener(this::onClick);
        //

        return view;
    }

    @Override
    public void onClick(View view) {
        String[] linuxDollTalking = {
                "Hey,Iam Linux,Dangerous OS Alive!",
                "Install Linux OS And Drive Things Crazy",
                "I Love People Who Use Me As OS!",
                "Do Not Be Limited, But Lets Limit Them!",
                "Linux! Linux! Linux! Linux!",
                "I Break Accounts, I Linux!",
                "I Help Both White Hats And Black Hats !",
                "I Reverse Systems When Commanded, I Linux!",
        };

        Random random = new Random();
        int picked = random.nextInt(7);

        Toasty.custom(getActivity(), linuxDollTalking[picked], R.drawable.ic_baseline_emoji_smile, R.color.teal_200, Toasty.LENGTH_LONG, true, true).show();
        imageViewLinuxDoll.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_top));

    }
}
