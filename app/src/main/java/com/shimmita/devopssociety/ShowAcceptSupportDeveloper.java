package com.shimmita.devopssociety;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;

public class ShowAcceptSupportDeveloper extends BottomSheetDialogFragment implements View.OnClickListener {
    Button button;
    Animation animation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.general_bottom_sheet, container, false);
        //
        button = view.findViewById(R.id.buttonAcceptSupportingDeveloper);
        button.setOnClickListener(this::onClick);

        //
        return view;
    }

    @Override
    public void onClick(View view) {

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in);
        button.startAnimation(animation);
        functionStartIntentNumber();

    }

    private void functionStartIntentNumber() {
        dismiss();
        Intent numberIntent = new Intent();
        numberIntent.setAction(Intent.ACTION_DIAL);
        numberIntent.setData(Uri.parse("tel:+254757450727"));
        startActivity(numberIntent);
        CustomIntent.customType(getActivity(), "fadein-to-fadeout");
        Toasty.custom(getActivity(), "Developers Number Forwarded Successfully", R.drawable.ic_baseline_call_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();


    }

}
