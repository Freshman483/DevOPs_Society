package com.shimmita.devopssociety.fragments_drawer_main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.mains.ShowAcceptSupportDeveloper;

public class BuyMeCoffeeFragmentClass extends Fragment implements View.OnClickListener {

    CardView cardView;
    Animation animation;

    public BuyMeCoffeeFragmentClass() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buy_me_coffee, container, false);
        //code here using Viewer mode
        cardView = view.findViewById(R.id.cardBuyCoffee);
        cardView.setOnClickListener(this::onClick);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout);
        //
        return view;
    }

    @Override
    public void onClick(View view) {
        cardView.startAnimation(animation);
        callAlertPaymentSupportDeveloper();
    }

    private void callAlertPaymentSupportDeveloper() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Support Developer")
                .setCancelable(false)
                .setIcon(R.drawable.ic_baseline_payment_24)
                .setMessage("\nBy Accepting Support The Developer Through M-Pesa, A Phone Number Will Be Forwarded To The  Dial Screen " +
                        "Of Your Smartphone. Use It To Surprise The Developer With Something.\nThank You.")
                .setPositiveButton("Absolutely, I Do", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //showSupportDeveloper
                        ShowAcceptSupportDeveloper showAcceptSupportDeveloper = new ShowAcceptSupportDeveloper();
                        showAcceptSupportDeveloper.show(getActivity().getSupportFragmentManager(), "showAny");

                        //

                    }
                }).create().show();

    }
}
