package com.shimmita.devopssociety;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;

public class HomeFragmentClass extends Fragment implements View.OnClickListener {
    public static final String TAG = "HomeFragmentClass";

    Button button;
    ImageView imageView_show_images;
    AnimationDrawable animationDrawableColours, animationDrawableImages, animationDrawableRelativeLayoutColours;
    ConstraintLayout constraintLayout;
    RelativeLayout relativeLayout;

    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //code here using Viewer mode
        button = (Button) view.findViewById(R.id.button_proceed_home_fragment);
        constraintLayout = view.findViewById(R.id.home_fragment_layout_parent);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.RelativeLayoutHomeFragment);
        imageView_show_images = (ImageView) view.findViewById(R.id.image_view_show_animation_home_fragment);

        //colourAnimationOnParentConstraintLayout
        animationDrawableColours = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawableColours.setEnterFadeDuration(3000);
        animationDrawableColours.setExitFadeDuration(3000);
//        animationDrawableColours.start();
        //


        //colourAnimationOnParentConstraintLayout
        animationDrawableRelativeLayoutColours = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawableRelativeLayoutColours.setEnterFadeDuration(3000);
        animationDrawableRelativeLayoutColours.setExitFadeDuration(3000);
//        animationDrawableColours.start();
        //


        //imageDisplayAnimation
        imageView_show_images.setBackgroundResource(R.drawable.animation_home_fragment_images);
        animationDrawableImages = (AnimationDrawable) imageView_show_images.getBackground();
//        animationDrawableImages.start();

        //addingOnClickListenerToTheImageview
        imageView_show_images.setOnClickListener(this::onClick);
        //


        //creatingNewThreadToHandleAnimations
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: thread:" + Thread.currentThread().getName());

                        animationDrawableImages.start();
                        animationDrawableColours.start();
                        animationDrawableRelativeLayoutColours.start();
                    }
                });
            }
        }).start();


        //buttons onclick listener
        button.setOnClickListener(this::onClick);
        //

        //
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.image_view_show_animation_home_fragment) {
            if (animationDrawableImages.isRunning()) {
                animationDrawableImages.stop();

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in);
                imageView_show_images.startAnimation(animation);
                button.startAnimation(animation);

                Toasty.custom(getActivity(), "Paused", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_SHORT, true, true).show();

            } else {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_out);
                imageView_show_images.startAnimation(animation);
                button.startAnimation(animation);
                animationDrawableImages.start();
                Toasty.custom(getActivity(), "Started", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_SHORT, true, true).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    new VibratorLowly(getActivity());
                }
            }

        } else if (view.getId() == R.id.button_proceed_home_fragment) {
            Animation animation = (Animation) AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_out);
            button.startAnimation(animation);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                new VibratorLowly(getActivity());
            }

            startActivity(new Intent(getActivity(), MainActivity.class));
            CustomIntent.customType(getActivity(), "fadein-to-fadeout");


        }

    }
}
